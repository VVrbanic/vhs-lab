# VHS Rental Store Application

In front of you is a simple application that helps VHS stores rent VHS tapes. This README.md will consist of three parts. The first part will show the structure of the database, the second part will go through the controllers showing the functionalities and the thought process behind them.

## 1. DATABASE

### Mysql (Mysql Workbench)

#### Tables:

**prices**
- `id` - int AI PK
- `active` - bool
- `date_from` - datetime
- `date_until` - datetime
- `price` - float

**rentals**
- `id` - int AI PK
- `due_date` - datetime
- `rented_date` - datetime
- `return_date` - datetime
- `user_id` - int (id --> users)
- `vhs_id` - int (id --> vhses)
- `due` - float
- `unpaid_due` - float

**users**
- `id` - int AI PK
- `name` - varchar
- `user_name` - varchar
- `password` - varchar
- `email` - varchar (hash password)

**vhses**
- `id` - int AI PK
- `name` - varchar
- `number_in_stock` - int
- `total_number` - int

## 2. ENTITIES

### PRICE CONTROLLER

A new price can be added. When a new price is added, the current active price is set to inactive and the `date_until` field is filled with the current time. If there is no active price, this step is skipped. The `date_from` field in the new price is filled with the same time as the `date_until` field from the previous active price.

**Next steps:**
- Update the active price automatically from a certain date.

### RENTAL CONTROLLER

The rental controller has `rentRental` and `returnRental` methods.

#### rentRental

When a VHS is rented, it is saved in the rentals table. The method first checks if there are VHS tapes in stock. The `rented_date` field is filled with the current date and the `due_date` is filled with the date that is a week later.

**Next steps:**
- If the user is inactive (the user's subscription expired), the user can't rent a VHS.
- If the user has a due, the user can't rent a VHS.

#### returnRental

When a VHS is returned, the `return_date` is filled with the current date. It also checks if the `return_date` is later than the `due_date`. If it is, it calculates the due ((`return_date` - `due_date`) * active_price) and updates the `total_due` and `unpaid_due` in the users table.

### USER CONTROLLER

You can delete a user if you mistyped something, but if a user rents something, you can't delete the user because the user is associated with a rental.

**Next steps:**
- Add two new fields, `active` and `subscription_date_valid_by`.
- If the `subscription_date_valid_by` passed, the `active` field automatically becomes false.

#### payDue

You can pay the due from the user. You can pay it in full or partially. If the payment is larger than or equal to the due, a message is sent explaining how much money needs to be given back. If the payment is smaller, a message is displayed showing the amount a user is due. If a user does not have a due, a message is shown with that information.

### VHS CONTROLLER

The VHS controller has `createVhs` and `getAll` methods.

#### getAll

Gets all the VHS tapes. If there are no VHS tapes, a `RentalException` is thrown.

#### createVhs

Creates a new VHS. It checks if there is any VHS with the same name. If there isn't, the VHS is saved. The `number_in_stock` field is set to the same value as the `total_number`.

**Next steps:**
- Add fields `genre_id` and table `genres` to have more information about the movies. We can also add actors, length, and other properties that can help people filter through VHS tapes.
- Implement an external API that can search on IMDB or some open-source application with details about the movies, which we can collect and save into our database.