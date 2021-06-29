package jp.co.internous.ecsite.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.internous.ecsite.model.entity.Goods;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
	List<Goods> findById(long id);

	@Transactional
	@Modifying
	@Query(value="update goods set goods_name = ?1, price = ?2 where id = ?3"
	,nativeQuery=true)
	void update(@Param("goodsName") String goodsName, 
											@Param("price") long price,
											@Param("id") long id);
}

