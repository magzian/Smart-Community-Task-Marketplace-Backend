package com.magz.backend.user.dtos;





import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyUserProfileDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("location")
    private String location;

    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @JsonProperty("doerRating")
    private Double doerRating;

    @JsonProperty("requesterRating")
    private Double requesterRating;

    @JsonProperty("joinedAt")
    private LocalDateTime joinedAt;

    @JsonProperty("skills")
    private List<String> skills;


    @Override
    public String toString() {
        return "MyUserProfileDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", doerRating=" + doerRating +
                ", requesterRating=" + requesterRating +
                ", joinedAt=" + joinedAt +
                ", skills=" + skills +
                '}';
    }

    public MyUserProfileDto() {
    }

    public MyUserProfileDto(Long id, String name, String email, String bio, String location, String avatarUrl, Double doerRating, Double requesterRating, LocalDateTime joinedAt, List<String> skills) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.location = location;
        this.avatarUrl = avatarUrl;
        this.doerRating = doerRating;
        this.requesterRating = requesterRating;
        this.joinedAt = joinedAt;
        this.skills = skills;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Double getDoerRating() {
        return doerRating;
    }

    public void setDoerRating(Double doerRating) {
        this.doerRating = doerRating;
    }

    public Double getRequesterRating() {
        return requesterRating;
    }

    public void setRequesterRating(Double requesterRating) {
        this.requesterRating = requesterRating;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
