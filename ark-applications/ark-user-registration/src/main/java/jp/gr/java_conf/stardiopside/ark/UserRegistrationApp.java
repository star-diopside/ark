package jp.gr.java_conf.stardiopside.ark;

import java.io.Console;
import java.text.MessageFormat;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;

import com.google.common.collect.Iterables;

import jp.gr.java_conf.stardiopside.ark.core.exception.ApplicationException;
import jp.gr.java_conf.stardiopside.ark.service.UserService;
import jp.gr.java_conf.stardiopside.ark.service.dto.UserDto;

@SpringBootApplication
public class UserRegistrationApp implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationApp.class);
    private final UserService userService;
    private final MessageSourceAccessor messages;

    public UserRegistrationApp(UserService userService, MessageSourceAccessor messages) {
        this.userService = userService;
        this.messages = messages;
    }

    @Override
    public void run(String... args) throws Exception {
        Options opt = new Options();
        opt.addOption(Option.builder("u").required().hasArg().argName("username").desc("ユーザ名を指定する。").build());
        opt.addOption(Option.builder("a").hasArg().argName("authority").desc("ユーザに設定する権限を指定する。").build());
        opt.addOption(Option.builder("p").hasArg().argName("password").desc("ユーザパスワードを指定する。").build());

        CommandLine cl;

        try {
            cl = new DefaultParser().parse(opt, args);
        } catch (ParseException e) {
            new HelpFormatter().printHelp("ark-user-registration -u <username> [-a <authority>] [-p <password>]", opt);
            return;
        }

        UserDto user = new UserDto();
        user.setUsername(cl.getOptionValue("u"));
        user.setDisplayName(cl.getOptionValue("u"));
        user.setPassword(Optional.ofNullable(cl.getOptionValue("p")).orElseGet(() -> {
            Console console = System.console();
            return String.valueOf(console.readPassword("パスワード: "));
        }));
        String authority = cl.getOptionValue("a", "ROLE_USER");

        try {
            userService.create(user, true, authority);
        } catch (ConstraintViolationException e) {
            e.getConstraintViolations().forEach(constraintViolation -> {
                System.err.println(getConstraintViolationMessage(constraintViolation));
            });
        } catch (ApplicationException e) {
            String errorMessage = e.isResource() ? messages.getMessage(e.getMessage()) : e.getMessage();
            System.err.println(errorMessage);
        }
    }

    private String getConstraintViolationMessage(ConstraintViolation<?> constraintViolation) {
        String propertyKey = Iterables.getLast(constraintViolation.getPropertyPath()).getName();
        String propertyName;

        try {
            propertyName = messages.getMessage(propertyKey);
        } catch (NoSuchMessageException e) {
            LOGGER.debug(e.getMessage(), e);
            propertyName = propertyKey;
        }

        return MessageFormat.format(constraintViolation.getMessage(), propertyName);
    }

    public static void main(String[] args) {
        SpringApplication.run(UserRegistrationApp.class, args);
    }
}
