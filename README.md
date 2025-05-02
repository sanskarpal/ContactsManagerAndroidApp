# Contacts Manager App

A modern Android application for managing contacts with a clean architecture and robust features.

## Features

- View a list of all contacts
- Add new contacts with name and email
- Delete contacts with swipe gesture
- Real-time UI updates with data changes

## Technologies

- **Language**: Java
- **Minimum SDK**: 29 (Android 10)
- **Target SDK**: 35

### Architecture Components

- **MVVM Architecture** (Model-View-ViewModel)
- **LiveData** - For observable data holder classes
- **ViewModel** - For UI-related data management
- **Repository Pattern** - For data operations abstraction
- **Room Database** - For local data persistence
- **Data Binding** - For binding UI components directly to data sources

### Libraries

- AndroidX Lifecycle Components
- Room Persistence Library (v2.7.0)
- RecyclerView for efficient list display
- ItemTouchHelper for swipe-to-delete functionality

## Architecture Overview

The app follows the MVVM (Model-View-ViewModel) architecture pattern:

1. **Model**:
    - `Contacts.java` - Entity class with Room annotations
    - `ContactDatabase.java` - Room database implementation
    - `ContactDAO.java` - Data Access Object interface for database operations

2. **View**:
    - `MainActivity.java` - Main activity displaying contacts list
    - `AddNewContactActivity.java` - Activity for adding new contacts
    - `MyAdapter.java` - RecyclerView adapter for contacts list

3. **ViewModel**:
    - `MyViewModel.java` - Manages UI-related data, survives configuration changes
    - Provides clean APIs for the UI to interact with data

4. **Repository**:
    - `Repository.java` - Abstraction layer between data sources and ViewModel
    - Manages background threading for database operations using Executor

## Implementation Details

### Database Operations

- Background thread processing for database operations using ExecutorService
- Main thread UI updates using Handler with main Looper

### Data Binding

The app uses data binding to eliminate boilerplate code:

- Click handlers separated into dedicated classes
- Direct binding between UI components and data sources

### Concurrency

Thread management for smooth user experience:

- Database operations run on background threads
- UI updates handled on the main thread

## Setup Instructions

1. Clone the repository
2. Open the project in Android Studio
3. Build and run on a device or emulator running Android 10 (API 29) or higher
