# Hexagonal Architecture Workshop

Level: Beginners will learn, intermediates will reinforce their knowledge, and advanced users will support others.

## Goal

Demonstrate how hexagonal design:

- Protect the domain from ripple effects from infra
- Isolates external changes (DTOs, APIs, adapters)
- Allows changes in infrastructure without breaking business logic

## Description

This workshop is designed in two practical and hands-on consisting in:
- Understand the **pain** of infrastructure coupling by **making infra changes** on an existent layered architecture codebase.
- Refactor to Hexagonal Architecture and apply the same changes to see how it protects the domain from them.

## Prerequisites

- Basic knowledge of Kotlin, Java engineers can follow along as well.

## Notes

- TESTS: While we will mention testing several times during the workshop, our focus is on understanding the pain of coupling and how hexagonal 
architecture can help mitigate it. For that reason, we won’t use tests to guide the exercise (e.g., TDD), or even include
them, as our goal is educational and tests would add unnecessary noise.
- IDE CAPABILITIES: Please do not use the refactoring capabilities of your IDE to rename classes, methods, or variables. 
This is to ensure that you understand the changes being made and their implications.

## Let's get ready

1. Clone the repository
2. Open the project in your IDE
3. Compile the project

## Workshop

The first pat will consist of making usual technology changes to the current nonhexagonal codebase and feel the pain of coupling.

[First Part](/workshop_steps/feel_the_pain/1_internal_api_contract_change.md)

The second part will consist of refactoring the code to use hexagonal architecture principles and see how the pain is mitigated.



