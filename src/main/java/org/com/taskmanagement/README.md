# Task Manager

## Overview
This example code for creating three flavors of a Task Manager.

While this particular problem could be solved as easily with inheritance/abstract classes, 
We chose composition over inheritance to allow future flexibility in what a task manager should
care about.

### Code packages
- org.com.taskmanagement contains interfaces and implementation for a task manager.
- org.com.taskmanagement.wrappers contains convenience wrappers for three flavors of task manager.
- org.com.taskmanagement.strategies contain the interface and implmentatino of the three strategies.

#### Caveats
- No semaphore, no locking. DO NOT use this in production.
- No failure management on killing proceses;
- No failure management or state persistence in case the task manager dies.


### Test location
- org.com.taskmanagement contains integration tests that test the public wrappers.

#### Caveat
- The tests are more integration tests which test the public api rather than each component separately.
This is a conscious choice by a lazy developer, and shouldn't be done in production. 
