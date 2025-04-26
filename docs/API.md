# API Class Documentation

**Package:** `dev.trindadedev.eltrinity.project.api`

---

## Overview

The `API` class offers core tools to interact with the ElTrinity project runtime.  
It allows adding views to the root layout, logging messages, running events on the UI thread, and managing lifecycle events easily.

---

## Fields

| Field | Type | Description |
| :--- | :--- | :--- |
| `lifecycleEvents` | `LifecycleEvents` | Lifecycle hooks for the associated Activity. |
---

## Methods

### Context & Activity Access

- **`BaseAPIActivity contextAsBaseAPIActivity()`**  
  Returns the context cast as `BaseAPIActivity`.  
  Throws an `IllegalStateException` if context is not a `BaseAPIActivity`.

- **`WindowManager getWindowManager()`**  
  Returns the `WindowManager` service.

- **`LayoutInflater getLayoutInflater()`**  
  Returns the `LayoutInflater` service.

### View Management

- **`addViewAtRoot(View view)`**  
  Adds a View to the root ViewGroup of the `BaseAPIActivity`.  
  If the root view is not available, logs an error.

### Toasts

- **`showToast(String message)`**  
  Displays a short Toast message (approximately 4000ms).

### Logging

- **`addTaskLog(String log)`**  
- **`addSuccessLog(String log)`**  
- **`addWarningLog(String log)`**  
- **`addErrorLog(String log)`**  
- **`addInfoLog(String log)`**

Each method adds a message to the corresponding log channel.

### UI Thread Event Execution

- **`onUiThread(Event event)`**  
  Runs a given `Event` on the UI thread via the interpreter.

---

## Inner Class: `LifecycleEvents`

Represents the lifecycle hooks for activities using this API.

### Fields

| Field | Type | Description |
| :--- | :--- | :--- |
| `onCreate` | `Event` | Called on activity creation. |
| `onResume` | `Event` | Called when activity resumes. |
| `onDestroy` | `Event` | Called when activity is destroyed. |
| `onStart` | `Event` | Called when activity starts. |
| `onPause` | `Event` | Called when activity pauses. |
| `onStop` | `Event` | Called when activity stops. |
| `onPostCreate` | `Event` | Called after `onCreate`. |

All events are initialized to empty events by default.

### Setters

- `setOnCreate(Event onCreate)`
- `setOnResume(Event onResume)`
- `setOnDestroy(Event onDestroy)`
- `setOnStart(Event onStart)`
- `setOnPause(Event onPause)`
- `setOnStop(Event onStop)`
- `setOnPostCreate(Event onPostCreate)`

Each setter updates the corresponding lifecycle event handler.

---

## Example Usage

```java

// Show a Toast
api.showToast("Welcome!");

// Add a custom view to root layout
final View customView = new TextView(context);
api.addViewAtRoot(customView);

// Add a success log
api.addSuccessLog("View successfully added!");

// Run an event on the UI thread
api.onUiThread(new Event() {
    public void onCallEvent() {
        // Code to execute on UI thread
    }
});