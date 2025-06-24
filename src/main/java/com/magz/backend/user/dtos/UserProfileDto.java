package com.magz.backend.user.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDto {
    private Long id;
    private String name;
    private String bio;
    private String avatarUrl;
    private String location;
    private Double doerRating;
    private Double requesterRating;
    private LocalDateTime joinedAt;


    public UserProfileDto() {
    }

    public UserProfileDto(Long id, String name, String bio, String avatarUrl, String location, Double doerRating, Double requesterRating, LocalDateTime joinedAt) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
        this.location = location;
        this.doerRating = doerRating;
        this.requesterRating = requesterRating;
        this.joinedAt = joinedAt;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
}