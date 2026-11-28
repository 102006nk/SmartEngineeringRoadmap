
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

    public List<Career> getAllCareers() throws SQLException {
        return careerDAO.getAllCareers();
    }

    public Career getCareerById(int careerId)
            throws SQLException {
        return careerDAO.getCareerById(careerId);
    }

    private static final Map<String, List<String>> CAREER_SKILLS =
            Map.ofEntries(

        Map.entry("java developer",
                List.of("Java", "OOP", "SQL",
                        "Data Structures")),

        Map.entry("data analyst",
                List.of("Python", "SQL", "Statistics",
                        "Data Visualization")),

        Map.entry("web developer",
                List.of("HTML", "CSS", "JavaScript")),

        Map.entry("cybersecurity engineer",
                List.of("Networking", "Linux", "Security")),

        Map.entry("mechanical design engineer",
                List.of("CAD", "Engineering Drawing",
                        "SolidWorks", "Mechanical Design")),

        Map.entry("electronics design engineer",
                List.of("Electronics", "Circuit Design",
                        "PCB Design")),

        Map.entry("cad engineer",
                List.of("CAD", "Engineering Drawing",
                        "SolidWorks")),

        Map.entry("civil design engineer",
                List.of("AutoCAD", "Structural Analysis",
                        "Civil Engineering")),

        Map.entry("electrical design engineer",
                List.of("Electrical Engineering",
                        "Circuit Design", "Electrical Design")),

        Map.entry("robotics engineer",
                List.of("Robotics", "Python",
                        "C++", "Control Systems")),

        Map.entry("vlsi design engineer",
                List.of("VLSI", "Verilog",
                        "Digital Electronics"))
    );

    public List<Skill> getRequiredSkillsForCareer(
            String careerName) throws SQLException {

        List<Skill> matchedSkills = new ArrayList<>();

        if (careerName == null
                || careerName.trim().isEmpty()) {
            return matchedSkills;
        }

        List<String> keywords = CAREER_SKILLS.get(
                careerName.trim().toLowerCase());

        if (keywords == null) {
            return matchedSkills;
        }

        List<Skill> allSkills = skillDAO.getAllSkills();

        for (Skill skill : allSkills) {

            for (String keyword : keywords) {

                if (skill.getSkillName()
                        .trim()
                        .equalsIgnoreCase(keyword.trim())) {

                    matchedSkills.add(skill);
                    break;
                }
            }
        }

        return matchedSkills;
    }
}
