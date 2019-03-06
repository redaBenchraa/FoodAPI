package com.food.api.rules;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleService {
	@Autowired
	RuleRepository ruleRepository;

	public List<Rule> GetAllRules(){
		return (List<Rule>) ruleRepository.findAll();
	}
}
