
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

    private final RoadmapDAO roadmapDAO =
            new RoadmapDAO();

    private final RoadmapItemDAO itemDAO =
            new RoadmapItemDAO();

    private final SubjectDAO subjectDAO =
            new SubjectDAO();

    private final CareerService careerService =
            new CareerService();

    private final StudentProfileService profileService =
            new StudentProfileService();

    // Generate roadmap
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

        List<Skill> skills =
                careerService.getRequiredSkillsForCareer(
                        careerName);

        if (skills.isEmpty()) {
            throw new IllegalArgumentException(
                    "No matching skills found for this career.");
        }

        List<Subject> subjects =
                subjectDAO.getSubjectsByBranchAndSemester(
                        profile.getBranchId(),
                        profile.getSemesterId());

        String description =
                "Personalized roadmap for " + careerName
                + " based on career skills and academic subjects.";

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

        int roadmapId =
                roadmapDAO.insertRoadmap(roadmap);

        List<RoadmapItem> items =
                buildRoadmapItems(
                        roadmapId,
                        skills,
                        subjects,
                        profile.getDailyStudyHours());

        for (RoadmapItem item : items) {
            itemDAO.insertRoadmapItem(item);
        }

        return roadmapId;
    }

    // Build ordered items
    private List<RoadmapItem> buildRoadmapItems(
            int roadmapId,
            List<Skill> skills,
            List<Subject> subjects,
            double studyHours) {

        List<RoadmapItem> items =
                new ArrayList<>();

        int intervalDays;

        if (studyHours <= 1) {
            intervalDays = 21;
        } else if (studyHours <= 2) {
            intervalDays = 14;
        } else if (studyHours <= 4) {
            intervalDays = 10;
        } else {
            intervalDays = 7;
        }

        int order = 1;

        for (Skill skill : skills) {

            String relatedSubjects =
                    findRelatedSubjects(
                            skill, subjects);

            String description =
                    "Develop the skill: "
                    + skill.getSkillName()
                    + ". "
                    + skill.getDescription()
                    + ". Related subjects: "
                    + relatedSubjects;

            RoadmapItem item = new RoadmapItem(
                    0,
                    roadmapId,
                    skill.getSkillId(),
                    "Learn " + skill.getSkillName(),
                    description,
                    order,
                    "PENDING",
                    LocalDate.now().plusDays(
                            order * intervalDays)
            );

            items.add(item);
            order++;
        }

        return items;
    }

    // Find related subjects
    private String findRelatedSubjects(
            Skill skill, List<Subject> subjects) {

        List<String> matches =
                new ArrayList<>();

        String skillName =
                skill.getSkillName()
                        .toLowerCase()
                        .trim();

        for (Subject subject : subjects) {

            String subjectName =
                    subject.getSubjectName()
                            .toLowerCase()
                            .trim();

            boolean match =
                    subjectName.contains(skillName)
                    || skillName.contains(subjectName);

            if (skillName.contains("sql")
                    || skillName.contains("database")) {

                match = match
                        || subjectName.contains("database")
                        || subjectName.contains("sql");
            }

            if (skillName.contains("network")) {

                match = match
                        || subjectName.contains("network");
            }

            if (skillName.contains("java")
                    || skillName.contains("programming")) {

                match = match
                        || subjectName.contains("programming")
                        || subjectName.contains("object oriented");
            }

            if (skillName.contains("python")) {

                match = match
                        || subjectName.contains("python")
                        || subjectName.contains("programming");
            }

            if (skillName.contains("security")
                    || skillName.contains("cybersecurity")) {

                match = match
                        || subjectName.contains("security")
                        || subjectName.contains("network");
            }

            if (match) {
                matches.add(
                        subject.getSubjectName());
            }
        }

        if (matches.isEmpty()) {
            return "No direct subject match found";
        }

        return String.join(", ", matches);
    }

    // Active roadmap
    public Roadmap getActiveRoadmap()
            throws SQLException {

        return roadmapDAO.getActiveRoadmapByUserId(
                SessionManager.getCurrentUserId());
    }

    // All roadmaps belonging to current user
    public List<Roadmap> getMyRoadmaps()
            throws SQLException {

        return roadmapDAO.getRoadmapsByUserId(
                SessionManager.getCurrentUserId());
    }

    // Retrieve items belonging to current user
    public List<RoadmapItem> getRoadmapItems(
            int roadmapId) throws SQLException {

        return itemDAO.getItemsByRoadmapId(
                roadmapId,
                SessionManager.getCurrentUserId());
    }

    // Update item status for current user
    public boolean updateItemStatus(
            int itemId, String status)
            throws SQLException {

        if (!"PENDING".equals(status)
                && !"IN_PROGRESS".equals(status)
                && !"COMPLETED".equals(status)) {

            throw new IllegalArgumentException(
                    "Invalid roadmap item status.");
        }

        return itemDAO.updateItemStatus(
                itemId,
                status,
                SessionManager.getCurrentUserId());
    }
}
