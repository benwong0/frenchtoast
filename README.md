The goal of FrenchToast is to create a more flexible and stylish Toast system for Android. It should maintain the basic functions of the Android Toast system while allowing more flexible customizations.

# Quick Start

```Java
// This example assumes it'll be used in an Activity. (this == Activity).

// Pixel density
float scale = getResources().getDisplayMetrics().density;

// Create a new toast with the text "Hello World". Toast will be dismissed in 15 seconds.
FrenchToast toast = FrenchToast.make(this, "Hello World", 15);

// Set padding around toast content, in this case, the text "Hello World".
toast.setPadding(Math.round(scale * 10), Math.round(scale * 5), Math.round(scale * 10), Math.round(scale * 5));

// Set a custom background. This could be color or image.
toast.setBackgroundResource(R.drawable.my_toast_background);

// Set a toast text color.
toast.setTextColor(ContextCompat.getColor(this, R.color.my_toast_text_color));

// Set toast text size.
toast.setTextSize(15);
```