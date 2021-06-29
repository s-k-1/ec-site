package jp.co.internous.ecsite.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.internous.ecsite.model.entity.Goods;

public interface UpdateRepository extends JpaRepository<Goods, Long> {
	@Query(value="SELECT update FROM goods WHERE goods_name = :goodsName",			
			nativeQuery=true)
	int findCountByGoodsName(@Param("goodsName") String goodsName);
}
	



