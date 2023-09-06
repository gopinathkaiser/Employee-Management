
(function () {
    // "it" function defines the test case
    function it(desc, func) {
        //encapsulate the func call in try/catch block so that testing does not stop if one test fails
        try {
            func();
            // If the test case passes then log the test case description in the browser console with a checkmark
            console.log('\x1b[32m%s\x1b[0m', '\u2714 ' + desc);
        } catch (error) {
            // log the error on the console with an 'x'
            console.log('\n');
            console.log('\x1b[31m%s\x1b[0m', '\u2718 ' + desc);
            console.error(error);
            console.log('\n');
        }
    }

    function assert(isTrue) {
        if (!isTrue) {
            throw new Error();
        }
    }


    it('valid Date check for 02/02/2023', function validateDob() {
        assert($myapp.isValidDate('02/02/2023'));
    });

    it('valid Date check for 02/02/1900', function validateDob() {
        assert($myapp.isValidDate('02/02/1900'));
    });

    it('Invalid Date check for 03/12/2022', function validateDob() {
        assert(!$myapp.isValidDate('03/12/2025'));
    });


    it('valid mobile number check for 9976898765', function validateMobileNum() {

        assert($myapp.isValidMobile(9976898765));
    });


    it('Invalid mobile number check for 5976898765 ', function validateMobileNum() {

        assert(!$myapp.isValidMobile(5976898765));
    });


    it('Invalid mobile number check for 98686868686777667', function validateMobileNum() {

        assert(!$myapp.isValidMobile(98686868686777667));
    });

    it('valid first name check for gopi', function validateFirstName() {

        assert($myapp.isValidFname("gopi"));
    });

    it('Invalid first name check for gopi45', function validateFirstName() {

        assert(!$myapp.isValidFname("gopi45"));
    });

    it('Invalid first name check for 4556', function validateFirstName() {

        assert(!$myapp.isValidFname("4556"));
    });

    it('valid Last name check for gopi', function validateLastName() {

        assert($myapp.isValidLname("gopi"));
    });

    it('Invalid Last name check for gopi45', function validateLastName() {

        assert(!$myapp.isValidLname("gopi45"));
    });

    it('Invalid Last name check for 4556', function validateLastName() {

        assert(!$myapp.isValidLname("4556"));
    });

    it('valid Email check for gopi@gmail.com', function validateEmail() {

        assert($myapp.isValidEmail("gopi@gmail.com"));
    });

    it('Invalid Email check for gopi!gmail.com', function validateEmail() {

        assert(!$myapp.isValidEmail("gopi!gmail.com"));
    });

    it('Invalid Email check for gopi@mail', function validateEmail() {

        assert(!$myapp.isValidEmail("gopi@mail"));
    });


   
})();
