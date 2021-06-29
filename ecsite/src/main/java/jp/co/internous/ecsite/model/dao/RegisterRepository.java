package jp.co.internous.ecsite.model.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.internous.ecsite.model.entity.User;

public interface RegisterRepository extends JpaRepository<User, Long> {
	List<User> findByUserNameAndPasswordAndFullName(String userName, String password,String fullName);
	// ↓usernameを探すよ。
	@Query(value="SELECT count(id) FROM user WHERE user_name = :userName",
			nativeQuery=true)
	int findCountByUserName(@Param("userName") String userName);
}


