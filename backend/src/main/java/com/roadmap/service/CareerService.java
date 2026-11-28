
package com.roadmap.service;

import com.roadmap.dao.CareerDAO;
import com.roadmap.dao.SkillDAO;
import com.roadmap.model.Career;
import com.roadmap.model.Skill;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CareerService {

    private final CareerDAO careerDAO = new CareerDAO();
    private final SkillDAO skillDAO = new SkillDAO();

    // Retrieve all careers
    public List<Career> getAllCareers() throws SQLException {
        return careerDAO.getAllCareers();
    }

    // Retrieve career by ID
    public Career getCareerById(int careerId)
            throws SQLException {
        return careerDAO.getCareerById(careerId);
    }

    // Initial rule-based career-to-skill mapping
    private static final Map<String, List<String>> CAREER_SKILLS =
            Map.of(
                "java developer",
                List.of("Java", "OOP", "SQL", "Data Structures"),

                "data analyst",
                List.of("Python", "SQL", "Statistics",
                        "Data Visualization"),

                "web developer",
                List.of("HTML", "CSS", "JavaScript"),

                "cybersecurity engineer",
                List.of("Networking", "Linux", "Security")
            );

    // Retrieve skills relevant to a career
    public List<Skill> getRequiredSkillsForCareer(
            String careerName) throws SQLException {

        List<Skill> allSkills = skillDAO.getAllSkills();
        List<Skill> matchedSkills = new ArrayList<>();

        if (careerName == null) {
            return matchedSkills;
        }

        List<String> keywords = CAREER_SKILLS.get(
                careerName.trim().toLowerCase());

        if (keywords == null) {
            return matchedSkills;
        }

        for (Skill skill : allSkills) {

            for (String keyword : keywords) {

                if (skill.getSkillName()
                        .equalsIgnoreCase(keyword)) {

                    matchedSkills.add(skill);
                    break;
                }
            }
        }

        return matchedSkills;
    }
}
