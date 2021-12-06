Feature: parking slot
    Scenario: allocate parking slot to vehicle
      Given a free parking slot
      When user request for a parking slot
      And provided vehicle reg number "KA 03 MZ 1469" and contact "987456123"
      Then should allocate the parking slot

    Scenario: re-allocate parking slot to vehicle
      Given parking slot with "1"
      When user request for a re allocation for "1" hour
      Then should extend vehicle out time

    Scenario: de-allocate parking slot to vehicle
      Given parking slot with "1"
      When user leave the parking slot
      Then parking slot should be de allocated