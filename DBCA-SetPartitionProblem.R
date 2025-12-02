# SCRIPT DE R PARA ANÁLISIS DE DISEÑO POR BLOQUES COMPLETOS ALEATORIZADOS (DBCA)
# EXPERIMENTO: TIEMPO DE EJECUCIÓN DEL ALGORITMO SET PARTITION EN 4 IDES Y 3 PCS

#################################################
# 1. INSTALACIÓN Y CARGA DE PAQUETES
#################################################

# se deben instalar los paquetes (solo la primera vez)
# install.packages(c("agricolae", "tseries", "car", "lmtest", "asbio"))

# para cargar los paquetes
library(agricolae)
library(tseries)
library(car)
library(lmtest)
library(asbio)

# NOTA: Debe crear un archivo de texto/csv (ej: datos_dbca.txt)
# con al menos las siguientes columnas:
# 'tiempo' (variable de respuesta, en segundos)
# 'IDE' (Factor de Interés, con los nombres de los IDEs)
# 'PC' (Factor de Bloqueo, con nombres como PC1, PC2, PC3)

#################################################
# 2. LECTURA DE DATOS Y DEFINICIÓN DEL MODELO
#################################################

# Para leer los datos cuando ya se tiene la columna de resultados
# Debe seleccionar el archivo de texto/csv que creó con sus datos.
datos <- read.table(file.choose(), header=TRUE)
datos

# Adjuntar los datos para fácil acceso (opcional, pero útil)
attach(datos)

# Definición del Modelo DBCA (Y ~ Tratamientos + Bloques)
# tiempo ~ IDE (Factor de Interés) + PC (Factor de Bloqueo)
mod_dbca <- aov(tiempo ~ IDE + PC, data=datos)

#################################################
# 3. ANÁLISIS DE VARIANZA (ANOVA)
#################################################

# Tabla ANOVA y Resumen del Modelo
# Hipótesis: H0: Las medias de los IDEs son iguales. Ha: Al menos dos medias difieren.
# Se rechaza H0 si el Valor P para el Factor IDE es < 0.05.
summary(mod_dbca)

# Si se desea verificar con el valor de F tabulado (requiere Grados de Libertad y MSE de la tabla ANOVA)
# qf(0.05, Gl_Factor_IDE, Gl_Error, lower.tail=FALSE)

# Si NO se rechaza H0 para el IDE, el análisis concluye aquí.
# Si se rechaza H0, se procede a las comparaciones múltiples.

# Cálculo de Efectos del modelo (Tau y Beta)
model.tables(mod_dbca, 'effect')

#################################################
# 4. EVALUACIÓN DEL FACTOR DE BLOQUEO y ADITIVIDAD
#################################################

# Evaluación del Factor de Bloqueo (PC)
# Si el Valor P para el Factor PC en la tabla ANOVA es < 0.05,
# el bloqueo fue efectivo y necesario.

# Prueba de Aditividad de Tukey
# Hipótesis: H0: El modelo es aditivo (No hay interacción IDE * PC). 
# Ha: El modelo no es aditivo (Hay interacción).
# Se rechaza H0 si el Valor P es < 0.05. (Esperamos no rechazar H0)
tukey.add.test(datos$tiempo, datos$IDE, datos$PC)

#################################################
# 5. EVALUACIÓN DE SUPUESTOS
#################################################

# Obtener los residuos del modelo
res <- residuals(mod_dbca)
ajustados <- fitted(mod_dbca)

### 6.1. Independencia (Orden de Experimentación)

## PRUEBA GRÁFICA: Residuales vs. Orden de Ejecución
plot(res, ylab='Residuales', xlab='Orden de experimentación', main='Independencia de Residuales', las=1, pch=20)
lines(res, col='red')
abline(h=0, lty=2)

## PRUEBA NUMÉRICA: Test de Rachas (Runs Test)
# H0: Los datos son independientes.
x <- factor(sign(res))
runs.test(x)

## PRUEBA NUMÉRICA: Test de Durbin-Watson
# H0: No hay autocorrelación (Independencia).
dwtest(mod_dbca, alternative="two.sided")

### 6.2. Normalidad

## PRUEBA GRÁFICA: Gráfico Cuantil-Cuantil (Q-Q Plot)
qqPlot(res, dist="norm", mean=0, sd=1, ylab='Residuales', xlab='Cuantiles Normales', main='Normalidad de Residuales', las=1)

## PRUEBA NUMÉRICA: Test de Shapiro-Wilk
# H0: La distribución de los residuales es normal.
shapiro.test(res)

### 6.3. Homogeneidad de Varianza (Homocedasticidad)

## PRUEBA GRÁFICA: Residuales vs. Valores Ajustados
plot(res, ajustados, las=1, ylab='Valores Ajustados', xlab='Residuales', main='Homogeneidad de Varianza', pch=20)
abline(h=0, lty=2) # En el DBCA se suele centrar en 0

## PRUEBA NUMÉRICA: Test de Bartlett (solo sobre el factor IDE)
# H0: La varianza es constante (homogénea) entre los tratamientos (IDEs).
bartlett.test(tiempo ~ IDE, data=datos)

#################################################
# 6. GRÁFICOS Y ANÁLISIS DE MEDIAS
#################################################

## Gráfico de Caja y Bigote (Boxplot) por IDE
boxplot(datos$tiempo ~ datos$IDE, horizontal = FALSE,
        col = c('blue', 'green', 'red', 'orange'), las=1,
        xlab='IDE', ylab='Tiempo (s)', 
        main='Tiempo de Ejecución por IDE')

# Calcular las medias por tratamiento
p <- model.tables(mod_dbca, "mean")

# Agregar puntos de las medias al gráfico
points(p$tables$IDE, pch = 20, cex=1.2)

#################################################
# 7. PRUEBAS DE COMPARACIÓN MÚLTIPLE
#################################################

# OJO: DEBE REEMPLAZAR 'Gl_Error' y 'MSE' con los valores obtenidos de su tabla summary(mod_dbca).

Gl_Error <- # [Grados de Libertad del Error, ej: 9] 
  MSE <- # [Media Cuadrada del Error (Residuals), ej: 0.936]
  
  # --- 8.1. Fisher (Mínima Diferencia Significativa, LSD) ---
  F_LSD <- LSD.test(datos$tiempo, datos$IDE, Gl_Error, MSE, alpha=0.05, group=TRUE)
F_LSD
plot(F_LSD, las=1) # Gráfico de medias y grupos

# --- 8.2. Tukey (Diferencia Significativa Honesta, HSD) ---
Tukey_HSD <- HSD.test(datos$tiempo, datos$IDE, Gl_Error, MSE, group=TRUE)
Tukey_HSD

# --- 8.3. Duncan (Rango Múltiple de Duncan) ---
Duncan_test <- duncan.test(datos$tiempo, datos$IDE, Gl_Error, MSE, group=TRUE)
Duncan_test