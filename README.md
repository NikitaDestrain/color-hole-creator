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
[INFO]: Reading img/source/example.jpg complete
[INFO]: Reading img/target/black-hole-example.jpg complete
[TEST]: Similar image MSE = 0.0
[RESULT]: MSE = 9.10963298949942E11
```
