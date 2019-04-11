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
# path in img folder (should contain descriptor and flist. See: dataset_example)
path = dataset_example
amount = 3
output_postfix = hole

## hole form
# rectangle / ellipse
form = rectangle

## hole sizes
# min hole height
min_height = 15
# max hole height
max_height = 50
# min hole width
min_width = 15
# max hole width
max_width = 50

## hole color
# red / green / blue / white / black
color = green
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