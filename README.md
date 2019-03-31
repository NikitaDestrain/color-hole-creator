# Color-hole-creator

Program can produce image with color hole (by default black color 0xff000000) and calculate statistical characteristics

### Hole creation example

####Parameters:

   - Color: default (0xff000000)
   - Size: default (1/6 of full image size)

#### Input

#### Output

### Statistical characteristic

At the moment, only calculation of MSE is available

IMAGE

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