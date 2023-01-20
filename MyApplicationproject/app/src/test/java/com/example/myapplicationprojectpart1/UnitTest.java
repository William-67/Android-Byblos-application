package com.example.myapplicationprojectpart1;

import org.junit.Test;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UnitTest {
    @Test
    public void testAccountMatchPassword(){
        UserAccount a = new UserAccount("1","will","123",Identity.customer);
        UserAccount b = new UserAccount("2","jack","456",Identity.employee);
        UserAccount c = new UserAccount("3","tom","789",Identity.customer);
        List<UserAccount> list = new LinkedList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        assertEquals("Test account match password fail",true,ListRecheck.match(list,"jack","456"));
        assertEquals("Test account match password fail",true,ListRecheck.match(list,"will","123"));
        assertEquals("Test account match password fail",true,ListRecheck.match(list,"tom","789"));
    }

    @Test
    public void testAccountIsDuplicated(){
        UserAccount a = new UserAccount("1","will","123",Identity.customer);
        UserAccount b = new UserAccount("2","jack","456",Identity.employee);
        UserAccount c = new UserAccount("3","tom","789",Identity.customer);
        List<UserAccount> list = new LinkedList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        assertEquals("Test duplicate account fail",true,ListRecheck.isDuplicate(list,"will"));
        assertEquals("Test duplicate account fail",true,ListRecheck.isDuplicate(list,"jack"));
        assertEquals("Test duplicate account fail",true,ListRecheck.isDuplicate(list,"tom"));
    }

    @Test
    public void testAccountDoesNotExist(){
        UserAccount a = new UserAccount("1","will","123",Identity.customer);
        UserAccount b = new UserAccount("2","jack","456",Identity.employee);
        UserAccount c = new UserAccount("3","tom","789",Identity.customer);
        List<UserAccount> list = new LinkedList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        assertEquals("Test not exist account fail",false,ListRecheck.isDuplicate(list,"bill"));
    }

    @Test
    public void testWorkingHour(){
        int startHour = 15;
        int startMin = 20;
        int engHour = 16;
        int endMin = 58;
        assertEquals("Test car rental Working hours failed",98,RequestCarRentalActivity.getWorkingTime(startHour,startMin,engHour,endMin),0.1);
        assertEquals("Test truck rental Working hours failed",98,RequestTruckRentalActivity.getWorkingTime(startHour,startMin,engHour,endMin),0.1);
        assertEquals("Test moving assistance Working hours failed",98,RequestedMovingAssistanceActivity.getWorkingTime(startHour,startMin,engHour,endMin),0.1);
    }

    @Test
    public void testServiceIsDuplicated(){
        String [] detail = {"q","w","e","r"};
        Service a = new Service("car rental",1000,"1", Arrays.asList(detail));
        Service b = new Service("truck rental",1150,"2", Arrays.asList(detail));
        Service c = new Service("moving assistance",100,"3", Arrays.asList(detail));
        List<Service> list = new LinkedList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        assertEquals("Test duplicate service fail",true,Service.isDuplicate(list,"car rental"));
        assertEquals("Test duplicate service fail",true,Service.isDuplicate(list,"truck rental"));
        assertEquals("Test duplicate service fail",true,Service.isDuplicate(list,"moving assistance"));
    }

    @Test
    public void testServiceDoesNotExist(){
        String [] detail = {"q","w","e","r"};
        Service a = new Service("car rental",1000,"1", Arrays.asList(detail));
        Service b = new Service("truck rental",1150,"2", Arrays.asList(detail));
        Service c = new Service("moving assistance",100,"3", Arrays.asList(detail));
        List<Service> list = new LinkedList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        assertEquals("Test not exist service fail",false,Service.isDuplicate(list,"haircut"));
    }


    @Test
    public void testScheduledServiceOnSameDate(){
        int startHour = 15;
        int startMin = 20;
        int engHour = 16;
        int endMin = 58;
        ScheduledServices a = new ScheduledServices("car rental","1","32","Hello",startHour,startMin,engHour,endMin, DayOfWeek.WEDNESDAY,"jack");
        ScheduledServices b = new ScheduledServices("truck rental","2","32","Hello",startHour,startMin,engHour,endMin, DayOfWeek.FRIDAY,"jack");
        ScheduledServices c = new ScheduledServices("moving rental","3","32","Hello",startHour,startMin,engHour,endMin, DayOfWeek.TUESDAY,"jack");
        List<ScheduledServices> list = new LinkedList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        assertEquals("Test scheduled service on same date fail",true,ScheduledServices.scheduledOnSameDate(list,"4","car rental",DayOfWeek.WEDNESDAY));
    }

    @Test
    public void testNotScheduledServiceOnSameDate(){
        int startHour = 15;
        int startMin = 20;
        int engHour = 16;
        int endMin = 58;
        ScheduledServices a = new ScheduledServices("car rental","1","32","Hello",startHour,startMin,engHour,endMin, DayOfWeek.WEDNESDAY,"jack");
        ScheduledServices b = new ScheduledServices("truck rental","2","32","Hello",startHour,startMin,engHour,endMin, DayOfWeek.FRIDAY,"jack");
        ScheduledServices c = new ScheduledServices("moving rental","3","32","Hello",startHour,startMin,engHour,endMin, DayOfWeek.TUESDAY,"jack");
        List<ScheduledServices> list = new LinkedList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        assertEquals("Test not scheduled service on same date fail",false,ScheduledServices.scheduledOnSameDate(list,"4","car rental",DayOfWeek.FRIDAY));
        assertEquals("Test not scheduled service on same date fail",false,ScheduledServices.scheduledOnSameDate(list,"5","moving rental",DayOfWeek.FRIDAY));
        assertEquals("Test not scheduled service on same date fail",false,ScheduledServices.scheduledOnSameDate(list,"6","moving assistance",DayOfWeek.TUESDAY));
    }

    @Test
    public void testScheduledServiceTimeBefore(){
        assertEquals("Test time before failed",true,ScheduledServices.before(10,30,15,20));
        assertEquals("Test time in same hour and time before failed",true,ScheduledServices.before(10,30,10,40));
    }

    @Test
    public void testScheduledServiceTimeAfter(){
        assertEquals("Test time after failed",true,ScheduledServices.after(16,30,15,20));
        assertEquals("Test time in same hour and time after failed",true,ScheduledServices.after(10,20,10,10));
    }

    @Test
    public void testScheduledServiceTimeConflict(){
        int startHour = 15;
        int startMin = 20;
        int engHour = 16;
        int endMin = 58;
        ScheduledServices a = new ScheduledServices("car rental","1","32","Hello",1,0,2,0, DayOfWeek.WEDNESDAY,"tom");
        ScheduledServices b = new ScheduledServices("truck rental","2","32","Hello",3,0,4,0, DayOfWeek.FRIDAY,"jack");
        ScheduledServices c = new ScheduledServices("moving rental","3","32","Hello",5,0,6,0, DayOfWeek.TUESDAY,"will");
        List<ScheduledServices> list = new LinkedList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        assertEquals("Test scheduled service time conflict fail",false,ScheduledServices.timeConflict(list,"4","jack",DayOfWeek.FRIDAY,startHour,startMin,engHour,endMin));


    }



}
