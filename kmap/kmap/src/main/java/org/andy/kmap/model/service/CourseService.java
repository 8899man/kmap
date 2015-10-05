package org.andy.kmap.model.service;

import java.util.List;

import org.andy.kmap.model.dao.*;
import org.andy.kmap.model.entity.*;
import org.andy.kmap.model.map.CourseMap;

import com.google.gson.Gson;

public class CourseService {

    private CourseDao courseDao;
    private CategoryDao categoryDao;
    private PropertyDao propertyDao;


    public void setCourseDao(CourseDao courseDao) {

        this.courseDao = courseDao;
    }


    public void setCategoryDao(CategoryDao categoryDao) {

        this.categoryDao = categoryDao;
    }


    public void setPropertyDao(PropertyDao propertyDao) {

        this.propertyDao = propertyDao;
    }


    private List<Course> setCategory(List<Course> courses) {

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            course.setCategory(this.categoryDao.getCategory(course));
        }

        return courses;
    }


    private List<Course> setProperty(List<Course> courses) {

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            course.setProperty(this.propertyDao.getProperty(course));
        }

        return courses;
    }


    private List<Course> setMark(User user, List<Course> courses) {

        for (Course course : courses) {
            this.courseDao.setMark(user, course);
        }

        return courses;
    }


    /**
     * This method gets a course map without user informaton.
     * @param major
     * @return
     */
    public String getCourseMap(Major major) {

        CourseMap map = new CourseMap(major);
        List<Course> courses = this.courseDao.getCourses(major);

        courses = this.setCategory(courses);
        courses = this.setProperty(courses);

        for (int i = 0; i < courses.size(); i++) {
            Course tail = courses.get(i);
            List<Course> heads = courseDao.getHeads(major, tail);
            List<Course> tails = courseDao.getTails(major, tail);

            if (heads.size() > 0 || tails.size() > 0) {
                map.addNode(tail);
            }

            if (heads.size() > 0) {
                for (Course head : heads) {
                    map.addEdge(tail, head);
                }
            }
        }

        return new Gson().toJson(map);
    }


    /**
     * This method gets a course map with user information.
     * @param user
     * @return
     */
    public String getCourseMap(User user) {

        CourseMap map = new CourseMap(user.getMajor());
        List<Course> courses = this.courseDao.getCourses(user.getMajor());

        courses = this.setCategory(courses);
        courses = this.setProperty(courses);
        courses = this.setMark(user, courses);

        for (int i = 0; i < courses.size(); i++) {
            Course tail = courses.get(i);
            List<Course> heads = courseDao.getHeads(user.getMajor(), tail);
            List<Course> tails = courseDao.getTails(user.getMajor(), tail);

            if (heads.size() > 0 || tails.size() > 0) {
                map.addNode(tail);
            }

            if (heads.size() > 0) {
                for (Course head : heads) {
                    map.addEdge(tail, head);
                }
            }
        }

        return new Gson().toJson(map);
    }
}
