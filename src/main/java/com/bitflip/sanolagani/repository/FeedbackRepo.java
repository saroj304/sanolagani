package com.bitflip.sanolagani.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitflip.sanolagani.models.Feedback;

public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {

}
