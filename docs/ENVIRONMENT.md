# Environment Variables Documentation

This document describes the environment variables used within the ElTrinity runtime environment.

---

## Variables

### `context`
- **Type:** `Context`
- **Description:**  
  The Android `Context` object representing the current application or activity environment.
- **Usage:**  
  Provides access to application resources, services, and system-level operations.

---

### `project`
- **Type:** `Project`
- **Description:**  
  The current project object managed by ElTrinity.
- **Usage:**  
  Contains metadata and configurations related to the running project, such as name, description, and files.
  See [Project Docs](https://github.com/trindadedev13/eltrinity/tree/main/docs/PROJECT.md)

---

### `project_path`
- **Type:** `File`
- **Description:**  
  The file object representing the absolute filesystem path to the project's root directory.
- **Usage:**  
  Used for file operations, resource loading, and project-related storage tasks.

---

### `api`
- **Type:** `API`
- **Description:**  
  Instance of the `API` class providing helper methods for logging, view management, lifecycle event handling, and UI thread operations.
- **Usage:**  
  Simplifies access to key ElTrinity features during project runtime.
  See [API Docs](https://github.com/trindadedev13/eltrinity/tree/main/docs/API.md)

---