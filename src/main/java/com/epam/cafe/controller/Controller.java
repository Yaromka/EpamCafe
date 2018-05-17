package com.epam.cafe.controller;

import java.io.IOException;

import com.epam.cafe.command.Command;
import com.epam.cafe.command.CommandFactory;
import com.epam.cafe.connection.ConnectionPool;
import com.epam.cafe.constants.Paths;
import com.epam.cafe.content.HttpRequestHelper;
import com.epam.cafe.content.NavigationType;
import com.epam.cafe.content.RequestContent;
import com.epam.cafe.content.RequestResult;
import com.epam.cafe.exception.ServiceException;
import com.epam.cafe.manager.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

    @WebServlet("/controller")
    public class Controller extends HttpServlet {
        private static final Logger LOGGER = LogManager.getLogger(Controller.class);

        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            processRequest(request, response);
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            processRequest(request, response);
        }

        private void processRequest(HttpServletRequest request,HttpServletResponse response)
                throws ServletException, IOException {

            RequestContent requestContent = new RequestContent();
            HttpRequestHelper httpRequestHelper = new HttpRequestHelper(requestContent);
            httpRequestHelper.getDataFromHttpRequest(request);

            CommandFactory factoryCommand = new CommandFactory();
            String commandName = requestContent.getCommandName();
            Command command = factoryCommand.createCommand(commandName);

            RequestResult requestResult;
            String page;
            NavigationType navigationType;

            try {
                requestResult  = command.execute(requestContent);
                page = requestResult.getPage();
                navigationType = requestResult.getNavigationType();
            } catch (ServiceException e) {
                LOGGER.error("An exception occurred while processing request: ", e);
                throw new ServletException("An exception occurred while processing request: ", e);
            }

            httpRequestHelper.addDataToHttpRequest(request);

            forwardOrRedirect(request, response, page, navigationType);
        }

        private void forwardOrRedirect(HttpServletRequest request, HttpServletResponse response, String page,
                                       NavigationType navigationType) throws ServletException, IOException {
            if (page == null) {
                page = request.getContextPath() + ConfigurationManager.getProperty(Paths.ERROR_PAGE);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
                requestDispatcher.forward(request, response);
            }

            if (navigationType == NavigationType.FORWARD) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
                requestDispatcher.forward(request, response);
            }

            if (navigationType == NavigationType.REDIRECT) {
                response.sendRedirect(page);
            }
        }

        @Override
        public void destroy() {
            ConnectionPool.getInstance().terminatePool();
            super.destroy();
        }
    }

