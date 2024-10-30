package com.incudo;

import com.incudo.controller.CourseController;
import com.incudo.controller.ReservationController;
import com.incudo.controller.UserController;
import com.incudo.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        ReservationController reservationController = new ReservationController();
        CourseController courseController = new CourseController();
        UserController userController = new UserController();

        ConsoleUI ui = new ConsoleUI(reservationController, courseController, userController);
        ui.start();
    }
}
