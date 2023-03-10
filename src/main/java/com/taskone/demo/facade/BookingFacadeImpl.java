package com.taskone.demo.facade;

import com.google.common.collect.Lists;
import com.taskone.demo.domain.Event;
import com.taskone.demo.domain.Ticket;
import com.taskone.demo.domain.User;
import com.taskone.demo.service.event.EventService;
import com.taskone.demo.service.ticket.TicketService;
import com.taskone.demo.service.user.UserService;
import com.taskone.demo.service.userAccount.UserAccountService;
import com.taskone.demo.utils.TicketCategoryEnum;
import com.taskone.demo.utils.exception.BookingServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class BookingFacadeImpl implements BookingFacade {
    private final EventService eventService;
    private final TicketService ticketService;
    private final UserService userService;
    private final UserAccountService userAccountService;

    @Override
    public Event getEventById(final long eventId) {
        return eventService.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(final String title, final int pageSize, final int pageNum) {
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(final LocalDate day, final int pageSize, final int pageNum) {
        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(final Event event) {
        return eventService.createEvent(event);
    }

    @Override
    public Event updateEvent(final Event event) {
        return eventService.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(final long eventId) {
        return eventService.deleteEvent(eventId);
    }

    @Override
    public User getUserById(final long userId) {
        try {
            return userService.getUserById(userId);
        } catch (BookingServiceException exc) {
            return null;
        }
    }

    @Override
    public User getUserByEmail(final String email) {
        try {
            return userService.getUserByEmail(email);
        } catch (BookingServiceException exc) {
            return null;
        }
    }

    @Override
    public List<User> getUsersByName(final String name, final int pageSize, final int pageNum) {
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(final User user) {
        return userService.createUser(user);
    }

    @Override
    public User updateUser(final User user) {
        return userService.updateUser(user);
    }

    @Override
    public boolean deleteUser(final long userId) {
        return userService.deleteUser(userId);
    }

    @Override
    public Ticket bookTicket(final long userId, final long eventId, final int place, final TicketCategoryEnum ticketCategory) {
        try {
            return ticketService.bookTicket(userId, eventId, place, ticketCategory);
        } catch (BookingServiceException exp) {
            return null;
        }
    }

    @Override
    public List<Ticket> getBookedTickets(final User user, final int pageSize, final int pageNum) {
        try {
            return ticketService.getBookedTickets(user, pageSize, pageNum);
        } catch (BookingServiceException exc) {
            return Lists.newArrayList();
        }
    }

    @Override
    public List<Ticket> getBookedTickets(final Event event, final int pageSize, final int pageNum) {
        try {
            return ticketService.getBookedTickets(event, pageSize, pageNum);
        } catch (BookingServiceException exc) {
            return Lists.newArrayList();
        }
    }

    @Override
    public boolean cancelTicket(final long ticketId) {
        return ticketService.cancelTicket(ticketId);
    }

    @Override
    public boolean refillUserAccount(int userId, String amount) {
        try {
            userAccountService.depositMoneyToUserAccount(userId, amount);

            return true;
        } catch (BookingServiceException exc) {
            return false;
        }
    }
}
