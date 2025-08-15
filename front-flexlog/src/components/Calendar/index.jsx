import React from "react";
import {
  add,
  sub,
  format,
  startOfMonth,
  endOfMonth,
  getDaysInMonth,
  getDay,
} from "date-fns";
import Cell from "../Cell";
import { CalendarWrapper, CalendarGrid, MonthTitle } from "./styles";

const weeks = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

const Calendar = ({ value = new Date(), onChange = () => {} }) => {
  const start = startOfMonth(value);
  const end = endOfMonth(value);
  const daysInMonth = getDaysInMonth(value);

  const prefixDays = getDay(start);
  const suffixDays = 6 - getDay(end);

  const prevMonth = () => onChange(sub(value, { months: 1 }));
  const nextMonth = () => onChange(add(value, { months: 1 }));
  const prevYear = () => onChange(sub(value, { years: 1 }));
  const nextYear = () => onChange(add(value, { years: 1 }));

  const handleClickDate = (day) => {
    const newDate = new Date(value.getFullYear(), value.getMonth(), day);
    onChange(newDate);
  };

  const todayDay = value.getDate();

  return (
    <CalendarWrapper>
      <CalendarGrid>
        <Cell onClick={prevYear}>{"<<"}</Cell>
        <Cell onClick={prevMonth}>{"<"}</Cell>
        <div style={{ gridColumn: "span 3", display: "flex", alignItems: "center", justifyContent: "center" }}>
          <MonthTitle>{format(value, "LLLL yyyy")}</MonthTitle>
        </div>
        <Cell onClick={nextMonth}>{">"}</Cell>
        <Cell onClick={nextYear}>{">>"}</Cell>

        {weeks.map((w, i) => (
          <Cell key={w + i} className="week-label">
            {w}
          </Cell>
        ))}

        {Array.from({ length: prefixDays }).map((_, i) => (
          <Cell key={`prefix-${i}`} />
        ))}

        {Array.from({ length: daysInMonth }).map((_, i) => {
          const day = i + 1;
          const isActive =
            day === todayDay &&
            value.getMonth() === new Date().getMonth() &&
            value.getFullYear() === new Date().getFullYear();

          return (
            <Cell
              key={`day-${day}`}
              isActive={isActive}
              onClick={() => handleClickDate(day)}
            >
              {day}
            </Cell>
          );
        })}

        {Array.from({ length: suffixDays }).map((_, i) => (
          <Cell key={`suffix-${i}`} />
        ))}
      </CalendarGrid>
    </CalendarWrapper>
  );
};

export default Calendar;
