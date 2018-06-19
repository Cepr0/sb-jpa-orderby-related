package io.github.cepr0.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@RequiredArgsConstructor
@EnableJpaRepositories(considerNestedRepositories = true)
@SpringBootApplication
public class Application {
	
	private final ParentRepo parentRepo;
	private final ChildRepo childRepo;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@EventListener
	public void onReady(ApplicationReadyEvent e) {
		List<Parent> parents = parentRepo.saveAll(range(0, 10)
				.mapToObj(i -> new Parent("parent" + i))
				.collect(toList())
		);

		List<Child> children = new ArrayList<>();
		Random r = new Random();
		parents.forEach(parent -> range(0, r.nextInt(5) + 1).forEach(i -> children.add(new Child("child_" + i + "_" + parent.getId(), parent))));
		childRepo.saveAll(children);

		parentRepo.getProjectionWithCount(PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "total")))
				.getContent()
				.forEach(p -> {
					System.out.printf("%s; ", p.getId());
					System.out.printf("%s; ", p.getName());
					System.out.printf("%s%n", p.getTotal());
				});
	}
}
