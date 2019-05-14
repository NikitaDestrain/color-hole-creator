# Color-hole-creator

Program can produce image with color hole and calculate statistical characteristics

### Hole creation example

#### Parameters:

   - **Color:** 0xff000000
   - **Size:** 1/6 of full image size
   - **Position:** random

#### Input

![](https://github.com/NikitaDestrain/color-hole-creator/blob/master/readme-resources/example.jpg)

#### Output

![](https://github.com/NikitaDestrain/color-hole-creator/blob/master/readme-resources/black-hole-example.jpg)

### Configuration

Go to **img/${your dataset name}** and create **descriptor** file and **flist** file. See example: **img/dataset_example**.

Then you should go to **properties/** and choose **configuration.properties** file and paste to it needed parameters

#### Default configuration.properties

```
## run configuration
# path in img folder (should contain descriptor.properties and flist. See: dataset_example)
path = dataset_example
amount = 8
output_postfix = hole
input_sub_path = input
output_sub_path = output
inpaint_sub_path = inpaint

## hole form
# rectangle / ellipse
form = ellipse

## hole sizes
# min hole height
min_height = 100
# max hole height
max_height = 200
# min hole width
min_width = 150
# max hole width
max_width = 200

## hole color
# red / green / blue / white / black
color = red
```

### Statistical characteristic

At the moment, only calculation of MSE is available

![](https://github.com/NikitaDestrain/color-hole-creator/blob/master/readme-resources/mse.PNG)

```
I - the first image 
K - the second image
```

Program result:

```
[INFO]: Original image - 0001_gth.png
[INFO]: Inpaint image - 0001_out.png
[RESULT]: MSE = 2.714814302671216E12

...

[INFO]: Original image - 1001_gth.png
[INFO]: Inpaint image - 1001_out.png
[RESULT]: MSE = 1.7302327627768616E12

[INFO]: Original image - 1002_gth.png
[INFO]: Inpaint image - 1002_out.png
[RESULT]: MSE = 8.199025745305693E11

[RESULT]: MSE mean = 1.9415632207691824E12
[RESULT]: MSE unbiased sample variance = 2.0731934884411998E24
```
