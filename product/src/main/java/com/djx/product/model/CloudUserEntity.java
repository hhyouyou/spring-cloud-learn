package com.djx.product.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xukai
 * @date 2021-11-29 00:01:13
 */
@Data
@Entity
@Table(name = "cloud_user")
public class CloudUserEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int")
	private Integer id;

    @Column(name = "username", columnDefinition = "char")
	private String username;

    @Column(name = "password", columnDefinition = "char")
	private String password;

    @Column(name = "phone", columnDefinition = "char")
	private String phone;

	/**
	 * 创建时间
	 */
    @Column(name = "created_at", columnDefinition = "timestamp")
	private LocalDateTime createdAt;

	/**
	 * 创建人
	 */
    @Column(name = "created_by", columnDefinition = "int")
	private Integer createdBy;

	/**
	 * 最后更新时间
	 */
    @Column(name = "last_updated_at", columnDefinition = "timestamp")
	private LocalDateTime lastUpdatedAt;

	/**
	 * 最后更新人
	 */
    @Column(name = "last_updated_by", columnDefinition = "int")
	private Integer lastUpdatedBy;

}
