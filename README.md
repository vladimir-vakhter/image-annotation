# image_annotation
# Brief
A small standalone application in Java to enable a user to annotate an image with comments.

You might have seen this functionality in Microsoft Word or Google Docs when the user is able to attach a comment to a selected text region.

In similar fashion, a user will load up a JPG, PNG or GIF image from disk (these are the only image formats that supported).
The image is displayed to the user and the user can select rectangular regions from within the image and create annotations. 

The annotation contents are stored to the disk into a JSON file on each delete/save request. The name of the file with the annotations for a particular image is "imageName_annotations.json". The file is located in the same directory as the image.
