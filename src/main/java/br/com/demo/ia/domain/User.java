package br.com.demo.ia.domain;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public final class User implements UserDetails {
	
	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String login;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(nullable = false)
	private String password;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(nullable = false, updatable = false)
	private LocalDateTime createDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(nullable = false)
	private LocalDateTime updateDate;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private Boolean admin;
	
	public User(
			Long id,
			String name, 
			String login, 
			String password, 
			String email, 
			Boolean admin
	) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.password = password;
		this.createDate = LocalDateTime.now();
		this.updateDate = this.createDate;
		this.email = email;
		this.admin = admin;
	}
	
	public User(
			Long id,
			String name, 
			String login, 
			String email,
			Boolean admin
	) {
		this.id = id;
		this.name = name;
		this.login = login;
		this.updateDate = LocalDateTime.now();
		this.email = email;
		this.admin = admin;
	}
	
	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = getAdmin() != null && getAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
		return List.of(new SimpleGrantedAuthority(role));
	}
	
	@Override @JsonIgnore public String getUsername() { return getEmail(); }
	@Override @JsonIgnore public boolean isAccountNonExpired() { return true; }
	@Override @JsonIgnore public boolean isAccountNonLocked() { return true; }
	@Override @JsonIgnore public boolean isCredentialsNonExpired() { return true; }
	@Override @JsonIgnore public boolean isEnabled() { return true;	}

}
