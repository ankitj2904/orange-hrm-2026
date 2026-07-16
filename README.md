# Enterprise Selenium Java Web Automation Framework (OrangeHRM) ☕

A production-ready, thread-safe web automation framework developed using **Selenium WebDriver**, **Java**, and **TestNG** to execute end-to-end regression testing on the OrangeHRM web application. 

This repository leverages advanced architectural design patterns—including decoupled object repositories, custom reporting lifecycle listeners, native desktop windows automation wrappers, and a declarative Jenkins CI/CD pipeline—to deliver maximum framework resilience with low maintenance overhead.

---

## 🛠️ Tech Stack & Core Libraries

*   **UI Automation Engine:** Selenium WebDriver (v4.x)
*   **Core Language:** Java (JDK 11+)
*   **Test Runner Architecture:** TestNG (Manages test groups, parameterization, and parallel execution threads)
*   **Build Pipeline Engine:** Apache Maven (`pom.xml`)
*   **CI/CD Integration:** Native Jenkins Pipeline (`Jenkinsfile`)
*   **Desktop Dialog Utility:** AutoIt Integration (For native Windows OS interactions)
*   **Analytical Reporting Logs:** Extent Reports (Thread-safe tracking implementation)

---

## 🏛️ Project Architecture & Directory Layout

The workspace is organized to cleanly separate infrastructure utilities, window handlers, test elements, and page elements:

```text
ORANGE-HRM/
├── .github/                      # Source control repository workflows
├── AutoItExecutableFile/         # Pre-compiled AutoIt scripts (.exe) for Windows desktop dialog handling
├── reports/                      # Central test runtime execution output directory
│   └── screenshots/              # System failure captures mapped directly to running Test Case IDs
├── src/
│   ├── main/
│   │   └── java/
│   │       └── listeners/        # Thread-safe TestNG ITestListener hooks (Listeners.java)
│   │       └── models/           # Data models and payload object abstractions
│   │       └── ObjectRepository/ # Page Object Model (POM) mapping element definitions
│   │       └── utilities/        # Framework configuration properties and wait synchronizations
│   └── test/                     # Dedicated directory executing modular test logic suites
├── test-output/                  # Auto-generated target system test logs
├── Jenkinsfile                   # Declarative CI/CD pipeline code for Jenkins automation servers
├── testng.xml                    # Modular regression suite run configurations
└── pom.xml                       # Maven build profiles and lifecycle dependencies
