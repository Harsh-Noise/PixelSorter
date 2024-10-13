# PixelSorter
A program that takes an image and sorts the pixels by hue, saturation, or whatever V stands for, and exports it on a Hilbert curve

Classes:
  Unit.java - Basic building block of a Hilbert curve. Contains information describing its orientation, location on the x/y plane, as well as a method for iterating itself to expand the curve. Can contain an object.

  HilbertCurve.java - Contains the curve, an array of Units. Also used to iterate the whole curve.

  ImgSorter.java - Contains the main method. Imports an image file and exports a sorted image. Contains the methods required to sort pixels by h, s, or v; and then interface with a Hilbert curve to plot the sorted pixels on a buffered image.

