package user.entities;

import exception.business.BusinessException;
import exception.business.detailed_exceptions.UserAlreadyActive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {
    // CREATE USER WITH VALID PHONE NUMBER => STATUS IS ACTIVE & CREATEDAT IS SET
    @Test
    @DisplayName("Should create active user when valid phone number is provided")
    public void createActiveUser_validPhoneNumber()
    {
        // set up
        PasswordHash passwordHash = new PasswordHash("$2342haHkacnd");
        PersonName personName = new PersonName("Phan Hai Anh");
        Email email = new Email("Haianh2504077@gmail.com");
        PhoneNumber phoneNumber = new PhoneNumber("0912345678");
        UserRole userRole = UserRole.NORMAL_USER;

        // create user
        User user = new User(passwordHash, personName, phoneNumber, email, userRole);

        // assert
        Assertions.assertNull(user.getId()); // initially null before save in db
        Assertions.assertEquals(passwordHash, user.getPasswordHash());
        Assertions.assertEquals(personName, user.getName());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(phoneNumber, user.getPhoneNumber());
        Assertions.assertSame(UserRole.NORMAL_USER, user.getRole());
        Assertions.assertEquals(UserStatus.ACTIVE, user.getStatus());
        Assertions.assertNotNull(user.getTimeCreated());
    }

    // CREATE USER WITHOUT PHONE NUMBER - STATUS: PENDING
    @Test
    @DisplayName("Should create pending user when not adding phone number initially")
    public void createPendingUser_NotAddingPhoneNumber()
    {
        // set up
        PasswordHash passwordHash = new PasswordHash("$2342haHkacnd");
        PersonName personName = new PersonName("Phan Hai Anh");
        Email email = new Email("Haianh2504077@gmail.com");
        UserRole userRole = UserRole.NORMAL_USER;

        // set up and create user
        User user = new User(passwordHash, personName, null, email, userRole);

        // assert
        Assertions.assertNull(user.getId());
        Assertions.assertEquals(passwordHash, user.getPasswordHash());
        Assertions.assertEquals(personName, user.getName());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertNull(user.getPhoneNumber());
        Assertions.assertSame(UserRole.NORMAL_USER, user.getRole());
        Assertions.assertSame(UserStatus.PENDING, user.getStatus());
        Assertions.assertNotNull(user.getTimeCreated());
    }

    // CREATE USER WITH MISSING REQUIRED DATA
    @Test // PASSWORD MISSING
    @DisplayName("Should reject user when password hash is missing")
    public void createUser_missingPasswordHash()
    {
        PersonName personName = new PersonName("Phan Hai Anh");
        Email email = new Email("Haianh2504077@gmail.com");

        // check if the system throw exceptions
        // assertThrows required 2 arguments
        // 1. the type of exception expected to happen
        // 2. the content code causing the exception
        NullPointerException exception = assertThrows(
                // .class -> metadata of a class
                NullPointerException.class,
                () -> User.builder()
                        .name(personName)
                        .email(email)
                        .userRole(UserRole.NORMAL_USER)
                        .build()
        );
        // The assertThrows return that exception & "exception" catch it content
        assertEquals("User passwordHash cannot be null", exception.getMessage());
    }

    @Test // NAME MISSING
    @DisplayName("Should reject user when name is missing")
    public void createUser_missingName()
    {
        PasswordHash passwordHash = new PasswordHash("$2342haHkacnd");
        Email email = new Email("Haianh2504077@gmail.com");

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> User.builder()
                        .passwordHash(passwordHash)
                        .email(email)
                        .userRole(UserRole.NORMAL_USER)
                        .build()
        );

        assertEquals("User name cannot be null", exception.getMessage());
    }

    @Test // EMAIL MISSING
    @DisplayName("Should reject user when email is missing")
    public void createUser_missingEmail()
    {
        PasswordHash passwordHash = new PasswordHash("$2342haHkacnd");
        PersonName personName = new PersonName("Phan Hai Anh");

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> User.builder()
                        .passwordHash(passwordHash)
                        .name(personName)
                        .userRole(UserRole.NORMAL_USER)
                        .build()
        );

        assertEquals("User email cannot be null", exception.getMessage());
    }

    @Test // ROLE MISSING
    @DisplayName("Should reject user when role is missing")
    public void createUser_missingRole()
    {
        PasswordHash passwordHash = new PasswordHash("$2342haHkacnd");
        PersonName personName = new PersonName("Phan Hai Anh");
        Email email = new Email("Haianh2504077@gmail.com");

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> User.builder()
                        .passwordHash(passwordHash)
                        .name(personName)
                        .email(email)
                        .build()
        );

        assertEquals("User role cannot be null", exception.getMessage());
    }

    // ACTIVATE AN ALREADY ACTIVE USER
    @Test
    @DisplayName("Activate an already active user")
    public void activateUser_alreadyActive(){
        // set up
        PasswordHash passwordHash = new PasswordHash("$2342haHkacnd");
        PersonName personName = new PersonName("Phan Hai Anh");
        Email email = new Email("Haianh2504077@gmail.com");
        PhoneNumber phoneNumber = new PhoneNumber("0912345678");
        UserRole userRole = UserRole.NORMAL_USER;

        // set up user
        User user = User.builder()
                .passwordHash(passwordHash)
                .email(email)
                .userRole(userRole)
                .phoneNumber(phoneNumber)
                .build();
        // catch exception - assert exception
        UserAlreadyActive exception = assertThrows(
                UserAlreadyActive.class,
                () -> user.activate(phoneNumber)
        );
        // assert message
        Assertions.assertEquals("User is already active",exception.getMessage());
    }

    // ACTIVATE A PENDING USER WITH NULL PHONE -> THROW EXCEPTION
    @Test
    @DisplayName("Activate a pending user with null phone number, throw exception")
    public void activateUser_nullPhoneNumber_throwException(){
        // set up
        PasswordHash passwordHash = new PasswordHash("$2342haHkacnd");
        PersonName personName = new PersonName("Phan Hai Anh");
        Email email = new Email("Haianh2504077@gmail.com");
        UserRole userRole = UserRole.NORMAL_USER;
        // set up user
        User user = User.builder()
                .passwordHash(passwordHash)
                .email(email)
                .userRole(userRole)
                .build();
        // catch and assert null exception
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> {
                    user.activate(null);
                }
        );
        // assert
        Assertions.assertEquals("PhoneNumber cannot be null", exception.getMessage());
    }
}
