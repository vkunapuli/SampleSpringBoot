package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class StudentController {

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		return cal;
	}

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String pong() {
		return "pong!";
	}

	@RequestMapping(value = "/getGradeOnGivenDate", method = RequestMethod.GET)
	public @ResponseBody int getItem(@RequestParam(value = "hsDate") String hsDate,
			@RequestParam(value = "givenDate", required = false) String givenDate) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date hs = (Date) formatter.parse(hsDate);
			Date given;
			if (givenDate == null || givenDate.isEmpty())
				given = new Date();
			else
				given = (Date) formatter.parse(givenDate);
			System.out.println(hsDate);
			System.out.println(givenDate);
			if (given.compareTo(hs) > 0)
				return 99;
			if (given.compareTo(hs) == 0)
				return 12;

			Calendar h = getCalendar(hs);
			Calendar g = getCalendar(given);
			Calendar start = Calendar.getInstance(Locale.US);
			int year = g.get(Calendar.YEAR);
			start.set(year, Calendar.AUGUST, 31);
			Calendar end = Calendar.getInstance(Locale.US);
			end.set(year+1, Calendar.JANUARY, 1);
			int diff = h.get(Calendar.YEAR) - g.get(Calendar.YEAR);
			if (g.after(start) && g.before(end))
				diff--;
			if (diff > 12)
				return -1;
			return 12 - diff;

		} catch (Exception ex) {
			return -1;
		}
	}
}
