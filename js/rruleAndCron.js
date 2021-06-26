function buildRecurrenceRule() {
  if (vm.selectedRecurrence.id !== 5) {
	vm.scheduledMessage.recurrenceRule = vm.selectedRecurrence.rrule;
  } else {
	var m = moment(vm.scheduledMessage.start, _DATE_TIME_FORMAT);
	vm.scheduledMessage.recurrenceRule = "FREQ=(f);INTERVAL=(i)".replace('(f)', vm.selectedRecurrence.freq.value).replace('(i)', vm.selectedRecurrence.interval);
	switch (vm.selectedRecurrence.freq.value) {
	  case 'WEEKLY':
		vm.scheduledMessage.recurrenceRule += ";BYDAY=" + Array.from(vm.selectedRecurrence.days.sort(function (a, b) {
		  return a.pos - b.pos;
		}), function (d) {
		  return d.value;
		}).toString();
		break;
	  case 'MONTHLY':
		vm.scheduledMessage.recurrenceRule += ";BYMONTHDAY=" + m.date();
		break;
	  case 'YEARLY':
		vm.scheduledMessage.recurrenceRule += ";BYMONTH=" + (m.month() + 1) + ";BYMONTHDAY=" + m.date();
		break;
	  default:
		return;
	}
  }
}

function convertRruleToCron(r) {
  if (!r) return; // <=> Only one time

  r = r.replace("RRULE:", "");

  const C_DAYS_OF_WEEK_RRULE = ["MO", "TU", "WE", "TH", "FR", "SA", "SU"];
  const C_DAYS_WEEKDAYS_RRULE = ["MO", "TU", "WE", "TH", "FR"];
  const C_DAYS_OF_WEEK_CRONE = ["2", "3", "4", "5", "6", "7", "1"];
  const C_DAYS_OF_WEEK_CRONE_NAMED = ["MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"];
  const C_MONTHS = ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"];

  var m = moment(vm.scheduledMessage.start, _DATE_TIME_FORMAT);
  var dayTime = "0 m h".replace('m', m.minute()).replace('h', m.hour());
  var dayOfMonth = "?";
  var month = "*";
  var dayOfWeek = "?";
  var year = "*";

  var FREQ = "";
  var INTERVAL = -1;
  var BYMONTHDAY = -1;
  var BYMONTH = -1;
  var BYDAY = "";
  var BYSETPOS = 0;

  var rArr = r.split(";");
  for (var i = 0; i < rArr.length; i++) {
	var param = rArr[i].split("=")[0];
	var value = rArr[i].split("=")[1];
	if (param === "FREQ") FREQ = value;
	if (param === "INTERVAL") INTERVAL = parseInt(value);
	if (param === "BYMONTHDAY") BYMONTHDAY = parseInt(value);
	if (param === "BYDAY") BYDAY = value;
	if (param === "BYSETPOS") BYSETPOS = parseInt(value);
	if (param === "BYMONTH") BYMONTH = parseInt(value);
  }
  if (FREQ === "MONTHLY") {
	if (INTERVAL === 1) {
	  month = "*"; // every month
	} else {
	  month = "1/" + INTERVAL; // 1 - start of january, every INTERVALth month
	}
	if (BYMONTHDAY !== -1) {
	  dayOfMonth = BYMONTHDAY.toString();
	} else if (BYSETPOS !== 0) {
	  if (BYDAY == "") {
		console.error("No BYDAY specified for MONTHLY/BYSETPOS rule");
		return INCASE_NOT_SUPPORTED;
	  }

	  if (BYDAY === "MO,TU,WE,TH,FR") {
		if (BYSETPOS === 1) {
		  // First weekday of every month
		  // "FREQ=MONTHLY;INTERVAL=1;BYSETPOS=1;BYDAY=MO,TU,WE,TH,FR",
		  dayOfMonth = "1W";
		} else if (BYSETPOS === -1) {
		  // Last weekday of every month
		  // "FREQ=MONTHLY;INTERVAL=1;BYSETPOS=-1;BYDAY=MO,TU,WE,TH,FR",
		  dayOfMonth = "LW";
		} else {
		  console.error(
			"Unsupported Xth weekday for MONTHLY rule (only 1st and last weekday are supported)"
		  );
		  return INCASE_NOT_SUPPORTED;
		}
	  } else if (C_DAYS_OF_WEEK_RRULE.indexOf(BYDAY) == -1) {
		console.error(
		  "Unsupported BYDAY rule (multiple days are not supported by crone): " +
		  BYDAY
		);
		return INCASE_NOT_SUPPORTED;
	  } else {
		dayOfMonth = "?";
		if (BYSETPOS > 0) {
		  // 3rd friday = BYSETPOS=3;BYDAY=FR in RRULE, 6#3
		  dayOfWeek =
			C_DAYS_OF_WEEK_CRONE[C_DAYS_OF_WEEK_RRULE.indexOf(BYDAY)] +
			"#" +
			BYSETPOS.toString();
		} else {
		  // last specific day
		  dayOfWeek =
			C_DAYS_OF_WEEK_CRONE[C_DAYS_OF_WEEK_RRULE.indexOf(BYDAY)] + "L";
		}
	  }
	} else {
	  console.error("No BYMONTHDAY or BYSETPOS in MONTHLY rrule");
	  return INCASE_NOT_SUPPORTED;
	}
  }

  if (FREQ === "WEEKLY") {
	if (INTERVAL !== 1) 
	  dayOfMonth = "1/" + INTERVAL * 7; // 1 week = 7 days. Ex : interval = 2 (every 2 weeks => 1/(2*7) <=> 1/14 <=> every 14 jours)
	if (
	  BYDAY.split(",")
		.sort()
		.join(",") ===
	  C_DAYS_OF_WEEK_RRULE.concat()
		.sort()
		.join(",")
	) {
	  dayOfWeek = "*"; // all days of week
	} else {
	  var arrByDayRRule = BYDAY.split(",");
	  var arrByDayCron = [];
	  for (var i = 0; i < arrByDayRRule.length; i++) {
		var indexOfDayOfWeek = C_DAYS_OF_WEEK_RRULE.indexOf(arrByDayRRule[i]);
		arrByDayCron.push(C_DAYS_OF_WEEK_CRONE_NAMED[indexOfDayOfWeek]);
	  }
	  dayOfWeek = arrByDayCron.join(",");
	}
  }

  if (FREQ === "DAILY") {
	if (INTERVAL != 1) {
	  dayOfMonth = "1/" + INTERVAL.toString();
	}
  }

  if (FREQ === "YEARLY") {
	if (BYMONTH == -1) {
	  console.error("Missing BYMONTH in YEARLY rule");
	  return INCASE_NOT_SUPPORTED;
	}
	month = C_MONTHS[BYMONTH - 1];
	if (BYMONTHDAY != -1) {
	  // FREQ=YEARLY;BYMONTH=3;BYMONTHDAY=2  // 2nd day of March
	  dayOfMonth = BYMONTHDAY;
	} else {
	  if (BYSETPOS == -1) {
		if (
		  BYDAY.split(",")
			.sort()
			.join(",") ===
		  C_DAYS_OF_WEEK_RRULE.concat()
			.sort()
			.join(",")
		) {
		  dayOfMonth = "L";
		} else if (
		  BYDAY.split(",")
			.sort()
			.join(",") ===
		  C_DAYS_WEEKDAYS_RRULE.concat()
			.sort()
			.join(",")
		) {
		  dayOfMonth = "LW";
		} else {
		  console.error(
			"Last weekends and just last specific days of Month are not supported"
		  );
		  return INCASE_NOT_SUPPORTED;
		}
	  } else {
		if (
		  BYDAY.split(",")
			.sort()
			.join(",") ===
		  C_DAYS_WEEKDAYS_RRULE.concat()
			.sort()
			.join(",") &&
		  BYSETPOS == 1
		) {
		  dayOfMonth = BYSETPOS.toString() + "W";
		} else if (BYDAY.split(",").length == 1) {
		  dayOfWeek =
			C_DAYS_OF_WEEK_CRONE[C_DAYS_OF_WEEK_RRULE.indexOf(BYDAY)] +
			"#" +
			BYSETPOS.toString();
		} else {
		  console.error("Multiple days are not supported in YEARLY rule");
		  return INCASE_NOT_SUPPORTED;
		}
	  }
	}
  }
  vm.scheduledMessage.cron = `${dayTime} ${dayOfMonth} ${month} ${dayOfWeek} ${year}`;
}