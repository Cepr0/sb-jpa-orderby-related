package io.github.cepr0.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParentRepo extends JpaRepository<Parent, Integer> {
	@Query(value = "" +
			"select " +
			"  p.id as id, " +
			"  p.name as name, " +
			"  count(c) as total " +
			"from " +
			"  Parent p " +
			"  left join Child c on c.parent.id = p.id " +
			"group by " +
			"  p, p.name " +
//			"order by " +
//			"  count(c) desc" +
			"", countQuery = "select count(p) from Parent p")
	Page<ParentWithCount> getProjectionWithCount(Pageable pageable);
}
