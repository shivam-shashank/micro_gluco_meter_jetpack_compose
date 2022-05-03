from operator import mod
import re
import numpy as np
import cv2
import skimage
import skimage.segmentation
import pickle
from skimage.feature import greycomatrix, greycoprops
from PIL import Image
import base64
import io
from os.path import dirname, join

filename = join(dirname(__file__), "model_pickle")
model = pickle.load(open(filename, "rb"))


def main(data):
    decoded_data = base64.b64decode(data)
    np_data = np.fromstring(decoded_data, np.uint8)
    img = cv2.imdecode(np_data, cv2.IMREAD_UNCHANGED)

    result = img
    img_bgr = result
    img_hsv = cv2.cvtColor(result, cv2.COLOR_BGR2HSV)
    img_lab = cv2.cvtColor(result, cv2.COLOR_BGR2LAB)
    img_xyz = skimage.color.rgb2xyz(result)
    img_gr = cv2.cvtColor(result, cv2.COLOR_BGR2GRAY)

    # Kurtosis function
    def kurtosis_color(x):
        x0 = x - mean_color(x)
        s2 = mean_color(x0**2)
        m4 = mean_color(x0**4)
        k = m4 / (s2**2)
        return k

    # Skewness function
    def skewness_color(x):
        x0 = x - mean_color(x)
        s2 = mean_color(x0**2)
        m3 = mean_color(x0**3)
        sk = m3 / (s2**1.5)
        return sk

    # Mean function
    def mean_color(img_input):
        sum = 0
        n = 1
        s = np.shape(img_input)
        for k in range(1, s[0]):
            for l in range(1, s[1]):
                if img_input[k, l] != 0:
                    sum = sum + img_input[k, l]
                    n += 1
        mean_col = sum / n
        return mean_col

    # Kurtosis
    B_kurtosis = kurtosis_color(img_bgr[:, :, 0])
    G_kurtosis = kurtosis_color(img_bgr[:, :, 1])
    R_kurtosis = kurtosis_color(img_bgr[:, :, 2])
    H_kurtosis = kurtosis_color(img_hsv[:, :, 0])
    S_kurtosis = kurtosis_color(img_hsv[:, :, 1])
    V_kurtosis = kurtosis_color(img_hsv[:, :, 2])
    L_kurtosis = kurtosis_color(img_lab[:, :, 0])
    La_kurtosis = kurtosis_color(img_lab[:, :, 1])
    Lb_kurtosis = kurtosis_color(img_lab[:, :, 2])
    # ntsc1_kurtosis =
    # ntsc2_kurtosis =
    # ntsc3_kurtosis =
    x_kurtosis = kurtosis_color(img_xyz[:, :, 0])
    y_kurtosis = kurtosis_color(img_xyz[:, :, 1])
    z_kurtosis = kurtosis_color(img_xyz[:, :, 2])

    # Skewness
    B_skewness = skewness_color(img_bgr[:, :, 0])
    G_skewness = skewness_color(img_bgr[:, :, 1])
    R_skewness = skewness_color(img_bgr[:, :, 2])
    H_skewness = skewness_color(img_hsv[:, :, 0])
    S_skewness = skewness_color(img_hsv[:, :, 1])
    V_skewness = skewness_color(img_hsv[:, :, 2])
    L_skewness = skewness_color(img_lab[:, :, 0])
    La_skewness = skewness_color(img_lab[:, :, 1])
    Lb_skewness = skewness_color(img_lab[:, :, 2])
    # ntsc1_skewness =
    # ntsc2_skewness =
    # ntsc3_skewness =
    x_skewness = skewness_color(img_xyz[:, :, 0])
    y_skewness = skewness_color(img_xyz[:, :, 1])
    z_skewness = skewness_color(img_xyz[:, :, 2])

    # Mean
    B_mean = mean_color(img_bgr[:, :, 0])
    G_mean = mean_color(img_bgr[:, :, 1])
    R_mean = mean_color(img_bgr[:, :, 2])
    H_mean = mean_color(img_hsv[:, :, 0])
    S_mean = mean_color(img_hsv[:, :, 1])
    V_mean = mean_color(img_hsv[:, :, 2])
    L_mean = mean_color(img_lab[:, :, 0])
    La_mean = mean_color(img_lab[:, :, 1])
    Lb_mean = mean_color(img_lab[:, :, 2])
    # ntsc1_mean =
    # ntsc2_mean =
    # ntsc3_mean =
    x_mean = mean_color(img_xyz[:, :, 0])
    y_mean = mean_color(img_xyz[:, :, 1])
    z_mean = mean_color(img_xyz[:, :, 2])

    # Entropy of image
    entropy = skimage.measure.shannon_entropy(img_gr)

    # Gray scale intensity
    intensity = mean_color(img_gr)

    # GLCM
    glcm = greycomatrix(
        img_gr, distances=[1], angles=[0], levels=256, symmetric=True, normed=True
    )

    contrast = greycoprops(glcm, "contrast")
    homogeneity = greycoprops(glcm, "homogeneity")
    energy = greycoprops(glcm, "energy")
    correlation = greycoprops(glcm, "correlation")

    array_input = [
        R_mean,
        G_mean,
        B_mean,
        H_mean,
        S_mean,
        V_mean,
        L_mean,
        La_mean,
        Lb_mean,
        x_mean,
        y_mean,
        z_mean,
        R_kurtosis,
        G_kurtosis,
        B_kurtosis,
        H_kurtosis,
        S_kurtosis,
        V_kurtosis,
        L_kurtosis,
        La_kurtosis,
        Lb_kurtosis,
        x_kurtosis,
        y_kurtosis,
        z_kurtosis,
        R_skewness,
        G_skewness,
        B_skewness,
        H_skewness,
        S_skewness,
        V_skewness,
        L_skewness,
        La_skewness,
        Lb_skewness,
        x_skewness,
        y_skewness,
        z_skewness,
        contrast,
        homogeneity,
        energy,
        correlation,
        entropy,
        intensity,
    ]

    return model.predict([array_input])
