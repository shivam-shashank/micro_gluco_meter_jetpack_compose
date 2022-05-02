import numpy as np
import cv2
import skimage
from skimage import morphology
from PIL import Image
import base64
import io


def main(data):
    decoded_data = base64.b64decode(data)
    np_data = np.fromstring(decoded_data, np.uint8)
    img = cv2.imdecode(np_data, cv2.IMREAD_UNCHANGED)

    disc = morphology.disk(1, np.uint8)

    temp1 = cv2.resize(img, (250, 250))
    temp2 = cv2.threshold(
        cv2.cvtColor(cv2.resize(img, (250, 250)), cv2.COLOR_BGR2GRAY),
        100,
        255,
        cv2.THRESH_OTSU,
    )
    eroded = cv2.erode(temp2[1], disc, iterations=6)
    eroded = cv2.dilate(eroded, disc, iterations=6)
    bbox = skimage.measure.regionprops(eroded)
    eroded = eroded[
        bbox[0].bbox[0] : bbox[0].bbox[2], bbox[0].bbox[1] : bbox[0].bbox[3]
    ]
    temp3 = temp1[:, :, 0][
        bbox[0].bbox[0] : bbox[0].bbox[2], bbox[0].bbox[1] : bbox[0].bbox[3]
    ]
    temp4 = temp1[:, :, 1][
        bbox[0].bbox[0] : bbox[0].bbox[2], bbox[0].bbox[1] : bbox[0].bbox[3]
    ]
    temp5 = temp1[:, :, 2][
        bbox[0].bbox[0] : bbox[0].bbox[2], bbox[0].bbox[1] : bbox[0].bbox[3]
    ]

    temp6 = np.stack([temp3, temp4, temp5], 2)

    # Mask input image with binary mask
    cleaned_conc = np.stack([eroded, eroded, eroded], 2)

    roi = cv2.bitwise_and(temp6, cleaned_conc)

    roi = roi[:, :, ::-1]

    pil_im = Image.fromarray(roi)

    buff = io.BytesIO()
    pil_im.save(buff, format="PNG")
    img_str = base64.b64encode(buff.getvalue())

    return "" + str(img_str, "utf-8")
