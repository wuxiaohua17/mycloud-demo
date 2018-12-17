package cn.com.ut.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {

	@Id
	@Column(name = "account_id")
	protected String accountId;

	@Column(name = "account_name")
	protected String accountName;

	@Column(name = "account_fund")
	protected BigDecimal accountFund;

	@Column(name = "update_time")
	protected Date updateTime;

}
