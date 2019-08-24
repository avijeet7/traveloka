package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * // Calendar implementation
 */

class Room {
  int id;
  String name;
  boolean available;
}

class Meeting {
  int id;
  String name;
  String employees;
}

@RestController
@RequestMapping("/api")
public class CalendarController {

  private HashMap<Integer, Room> rooms = new HashMap<>();
  private HashMap<Integer, String> employees = new HashMap<>();
  private HashMap<String, Meeting> meetings = new HashMap<>();
  private int roomCount = 0;
  private int meetCount = 0;
  private int empCount = 0;

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @GetMapping("/rooms")
  public HashMap<Integer, Room> getRoom(@RequestParam("id") int id, @RequestParam("name") String name) {
    Room newRoom = new Room();
    newRoom.id = id;
    newRoom.name =name;
    newRoom.available = true;

    rooms.put(id, newRoom);

    return rooms;
  }

  @GetMapping("/add-emp")
  public HashMap<Integer, String> addEmployee(@RequestParam(name="name", required=true, defaultValue="Avijeet") String name) {
    employees.put(empCount++, name);
    return employees;
  }

  @GetMapping("/list-emp")
  public HashMap<Integer, String> listEmployees() {
    return employees;
  }

  @GetMapping("/create-meeting")
  public int createMeeting(@RequestParam("name") String name, @RequestParam("employees") String emp) {
    //String[] s = emp.split(",");
    //for (String e: s) {
    //}
    Meeting newMeet = new Meeting();
    newMeet.id = meetCount++;
    newMeet.name = name;
    newMeet.employees = emp;

    meetings.put(name, newMeet);

    return meetings.size();
  }

  @GetMapping("/get-meeting")
  public String getMeeting(@RequestParam("name") String name) {

    String s = meetings.get(name).employees;

    return s;
  }

  @GetMapping("/get-employee-meetings-by-id")
  public ArrayList<String> getEmployeeMeetings(@RequestParam("id") String id) {
    //Iterator it = meetings.entrySet().iterator();
    //while (it.hasNext()) {
    //  Meeting m = it.next();
    //  it.remove();
    //}

    ArrayList<String> al = new ArrayList<>();

    String out = "Meetings for " + id + " are: ";

    for (Map.Entry<String,Meeting> entry : meetings.entrySet()) {
      String[] s = entry.getValue().employees.split(",");
      if(Arrays.asList(s).contains(id)){
        LOGGER.info(entry.getKey());
        out += entry.getKey() + " ";
        al.add(entry.getKey());
      }
      LOGGER.info(entry.getValue().employees);
    }

    //String s = meetings.get(id).employees;

    return al;
  }

}
