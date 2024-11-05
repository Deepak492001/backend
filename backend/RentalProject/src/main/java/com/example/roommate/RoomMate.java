package com.example.roommate;

import java.time.LocalDate;

import com.example.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
public class RoomMate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Integer numberOfBalconies;
	@Column(nullable = false)
	private Integer bathRooms;
	@Column(nullable = false)
	private String floorNumber;
	private Integer age;
	private String occupation;
	private String preference;

	@Column(length = 5000, nullable = false)
	private String roomImageUrl;
	private String roomImagePublicId;
	@Column(length = 5000, nullable = false)
	private String roomVideoUrl;
	@Column(nullable = false)
	private String roomVideoPublicId;
	@Column(nullable = false)
	private Double budget;
	@Column(length = 65535)
	private String description;
	@Column(nullable = false)
	private String landmark;
	@Column(nullable = false)
	private String state;
	@Column(nullable = false)
	private String city;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)

	@Temporal(TemporalType.DATE)
	private LocalDate availableFrom;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;



	public RoomMate(Integer numberOfBalconies, Integer bathRooms, String floorNumber, Integer age, String occupation,
			String preference, String roomImageUrl, String roomImagePublicId, String roomVideoUrl,
			String roomVideoPublicId, Double budget, String description, String landmark, String state, String city,
			String address, LocalDate availableFrom, User user) {
		this.numberOfBalconies = numberOfBalconies;
		this.bathRooms = bathRooms;
		this.floorNumber = floorNumber;
		this.age = age;
		this.occupation = occupation;
		this.preference = preference;
		this.roomImageUrl = roomImageUrl;
		this.roomImagePublicId = roomImagePublicId;
		this.roomVideoUrl = roomVideoUrl;
		this.roomVideoPublicId = roomVideoPublicId;
		this.budget = budget;
		this.description = description;
		this.landmark = landmark;
		this.state = state;
		this.city = city;
		this.address = address;
		this.availableFrom = availableFrom;
		this.user = user;
	}


}
