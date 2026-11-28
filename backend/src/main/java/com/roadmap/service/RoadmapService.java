
package com.roadmap.service;

import com.roadmap.dao.RoadmapDAO;
import com.roadmap.dao.RoadmapItemDAO;
import com.roadmap.dao.SubjectDAO;
import com.roadmap.model.Roadmap;
import com.roadmap.model.RoadmapItem;
import com.roadmap.model.Skill;
import com.roadmap.model.StudentProfile;
import com.roadmap.model.Subject;
import com.roadmap.util.SessionManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoadmapService {

    private final RoadmapDAO roadmapDAO = new RoadmapDAO();
    private final RoadmapItemDAO itemDAO = new RoadmapItemDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();

    private final CareerService careerService =
            new CareerService();

    private final StudentProfileService profileService =
            new StudentProfileService();

    // Generate roadmap for selected career
    public int generateRoadmap(String careerName)
            throws SQLException {

        if (careerName == null
                || careerName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Career name is required.");
        }

        StudentProfile profile =
                profileService.getCurrentProfile();

        if (profile == null) {
            throw new IllegalArgumentException(
                    "Create your student profile first.");
        }

        // Get career-required skills
        List<Skill> skills =
                careerService.getRequiredSkillsForCareer(
                        careerName);

        if (skills.isEmpty()) {
            throw new IllegalArgumentException(
                    "No matching skills found for this career. "
                    + "Check career skill mappings and seed data.");
        }

        // Get student's branch and semester subjects
        List<Subject> subjects =
                subjectDAO.getSubjectsByBranchAndSemester(
                        profile.getBranchId(),
                        profile.getSemesterId());

        // Build roadmap description
        String description =
                "Personalized roadmap for " + careerName
                + " based on required skills, branch and semester.";

        Roadmap roadmap = new Roadmap(
                0,
                SessionManager.getCurrentUserId(),
                careerName + " Learning Roadmap",
                careerName,
                description,
                LocalDate.now(),
                LocalDate.now().plusMonths(6),
                "ACTIVE"
        );

        // Save roadmap
        int roadmapId = roadmapDAO.insertRoadmap(roadmap);

        // Generate roadmap items
        List<RoadmapItem> items =
                buildRoadmapItems(roadmapId, skills, subjects);

        for (RoadmapItem item : items) {
            itemDAO.insertRoadmapItem(item);
        }

        return roadmapId;
    }

    // Build ordered roadmap items
    private List<RoadmapItem> buildRoadmapItems(
            int roadmapId,
            List<Skill> skills,
            List<Subject> subjects) {

        List<RoadmapItem> items = new ArrayList<>();

        int order = 1;

        for (Skill skill : skills) {

            String relatedSubjects =
                    findRelatedSubjects(skill, subjects);

            String description =
                    "Develop the skill: "
                    + skill.getSkillName()
                    + ". " + skill.getDescription()
                    + ". Relevant branch subjects: "
                    + relatedSubjects;

            RoadmapItem item = new RoadmapItem(
                    0,
                    roadmapId,
                    skill.getSkillId(),
                    "Learn " + skill.getSkillName(),
                    description,
                    order,
                    "PENDING",
                    LocalDate.now().plusDays(order * 14L)
            );

            items.add(item);

            order++;
        }

        return items;
    }

    // Find relevant subjects using keyword matching
    private String findRelatedSubjects(
            Skill skill, List<Subject> subjects) {

        List<String> matches = new ArrayList<>();

        String skillName =
                skill.getSkillName().toLowerCase();

        for (Subject subject : subjects) {

            String subjectName =
                    subject.getSubjectName().toLowerCase();

            if (subjectName.contains(skillName)
                    || skillName.contains(subjectName)) {

                matches.add(subject.getSubjectName());
            }
        }

        if (matches.isEmpty()) {
            return "No direct subject match found";
        }

        return String.join(", ", matches);
    }

    // Retrieve active roadmap for current user
    public Roadmap getActiveRoadmap()
            throws SQLException {

        return roadmapDAO.getActiveRoadmapByUserId(
                SessionManager.getCurrentUserId());
    }

    // Retrieve all roadmaps for current user
    public List<Roadmap> getMyRoadmaps()
            throws SQLException {

        return roadmapDAO.getRoadmapsByUserId(
                SessionManager.getCurrentUserId());
    }

    // Retrieve roadmap items
    public List<RoadmapItem> getRoadmapItems(
            int roadmapId) throws SQLException {

        return itemDAO.getItemsByRoadmapId(roadmapId);
    }

    // Update roadmap item status
    public boolean updateItemStatus(
            int itemId, String status) throws SQLException {

        if (!status.equals("PENDING")
                && !status.equals("IN_PROGRESS")
                && !status.equals("COMPLETED")) {

            throw new IllegalArgumentException(
                    "Invalid roadmap item status.");
        }

        return itemDAO.updateItemStatus(itemId, status);
    }
}
