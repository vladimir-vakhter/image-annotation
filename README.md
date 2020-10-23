# image_annotation
# Brief
A small standalone application in Java to enable a user to annotate an image with comments.

You might have seen this functionality in Microsoft Word or Google Docs when the user is able to attach a comment to a selected text region.

In similar fashion, a user will load up a JPG, PNG or GIF image from disk (these are the only image formats that supported).
The image is displayed to the user and the user can select rectangular regions from within the image and create annotations. 

The annotation contents are stored to the disk into a JSON file on each delete/save request. The name of the file with the annotations for a particular image is "imageName_annotations.json". The file is located in the same directory as the image.

# Architecture
The application is designed in accordance with the 'entity-boundary-control' (EBC) architectural paradigm.

You can find more information about this mindset on Wikipedia (https://en.wikipedia.org/wiki/Entity-control-boundary).

# Use cases

A use case represents the discrete transition of a system from one consistent state to another consistent state.

The following use cases are identified for this application domain:

## *1. Load Image*

Use Case Name: Load Image.

Participating Actor: Initiated by user.

Entry Condition: None.

Exit Criteria: Image and any accompanying annotations are loaded and displayed.

Flow of Events:
  1. User requests to load a new image.
  2. AnnotationApp loads the image and all accompanying annotations, then updates its display.
  
## *2. Select Annotation*

Use Case Name: Select annotation.

Participating Actor: Initiated by user.

Entry Condition: A supported image file must be loaded with at least one annotation.

Exit Criteria: Selected annotation is highlighted, and the application displays the annotation content.

Flow of Events:
  1. User clicks on an unselected annotation.
  2. AnnotationApp highlights the selected annotation and updates display to show annotation content.

## *3. Create Annotation*

Use Case Name: Create Annotation.

Participating Actor: Initiated by user.

Entry Condition: A supported image file must be loaded.

Exit Criteria: A new annotation is created, is visible as a rectangle on the image, is selected, and is saved.

Flow of Events:
  1. User selects a rectangular region for the annotation and provides the annotation content.
  2. AnnotationApp saves the annotation to disk and updates display with new annotation content.

## *4. Delete Annotation*

Use Case Name: Delete Annotation.

Participating Actor: Initiated by user.

Entry Condition: A supported image file must be loaded with, at least, one annotation.

Exit Criteria: The selected annotation is removed.

Flow of Events:
  1. User requests to delete selected annotation.
  2. AnnotationApp removes annotation from display and deletes selected annotation from disk.

## *5. Exit Application*

Use Case Name: Exit Application.

Participating Actor: Initiated by user.

Entry Condition: None.

Exit Criteria: App has quit.

Flow of Events:
  1. User requests to quit application.
  2. AnnotationApp saves all annotations and exits.

# Notes
* There is no use case “Edit Annotation” which allows a user to edit the annotation contents for an existing annotation. Instead, this must be carried out by deleting the old annotation region and then recreating it. This is called a “workaround”! Ultimately, a future version could offer this capability.
* Any point click within rectangle is sufficient for selecting annotation.
* No ability to close an image, only can load first image: when the application launches first,	no image is visible until the user loads one.
* Rectangle borders of annotations cannot completely overlap each other. If the user tries to draw a new annotation inside an existing one, or if the user tries to draw a new annotation that completely shadows an existing one, an alert message will appear in the bottom of the main application window and the newly drawn annotation will be discarded.
* The text fields which are used to enter and to display an annotation are both scrollable.
* The maximum resolution of input image is 640*480. The image panel is not scrollable, i.e. if the resolution	of input image exceeds the maximum allowed resolution, only the top left part of it will be displayed.

# Unit Test Coverage of the *src* folder
88.4%
