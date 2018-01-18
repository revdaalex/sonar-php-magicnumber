<?php

class Foo
{
    public $test = '13'; // Compliant

    public $test = 12; // Compliant

    const MAX_PASSWORD_LENGTH = 6; // Compliant

    public function setPassword($password)
    {
         if (mb_strlen($password) > 7) { // Noncompliant
              throw new InvalidArgumentException("password");
         }
    }

    public function setPassword($password)
    {
         if (mb_strlen($password) > self::MAX_PASSWORD_LENGTH) {
                  throw new InvalidArgumentException("password");
         }
    }
}

class Bar
{
   function apiVersion()
   {
      return 1.7; // Noncompliant
   }

   function getCount()
   {
      for ($i = 1; $i <= 10; $i++) { // Noncompliant
          return $i;
      }
   }
}

class Test
{
   private $variable = 6; // Compliant
   public function test($input = 4) { // Noncompliant
      if ($input > 2) { // Noncompliant
         return 15; // Noncompliant
      }
      round($input, 4); // Noncompliant
      round($input, $input > 7); // Noncompliant
      $array = [
         'name' => 'Name',
         'age' => 13, // Compliant
         'adult' => $input > 18, // Compliant
      ];
      if ($input > 1); // Compliant
      if (PHP_VERSION_ID < 50600); // Noncompliant
      $value = $input * 15; // Noncompliant
   }
   const TEST = 20 * 21; // Compliant
   public function returnString() {
      return 'string'; // Compliant
   }
   public function returnNegative() {
      return -2; // Noncompliant
   }
}