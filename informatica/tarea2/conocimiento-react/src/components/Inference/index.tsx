import React, {useEffect, useRef, Fragment} from "react";
import * as cocoSsd from "@tensorflow-models/coco-ssd";
import "@tensorflow/tfjs";
import "./styles.css";
import {DetectedObject} from "@tensorflow-models/coco-ssd";

function Inference() {
    const videoRef = useRef<HTMLVideoElement>(null)
    const canvasRef = useRef<HTMLCanvasElement>(null);

    useEffect(() => {
        if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia && videoRef != null && videoRef.current) {
            const webCamPromise = navigator.mediaDevices
                .getUserMedia({
                    audio: false,
                    video: {
                        facingMode: "user"
                    }
                })
                .then((stream: any) => {
                    window['stream'] = stream;
                    videoRef.current!.srcObject = stream;
                    return new Promise((resolve, reject) => {
                        videoRef.current!.onloadedmetadata = () => {
                            resolve();
                        };
                    });
                });
            const modelPromise = cocoSsd.load();
            Promise.all([modelPromise, webCamPromise])
                .then(values => {
                    detectFrame(videoRef.current, values[0]);
                })
                .catch(error => {
                    console.error(error);
                });
        }

    })


    function detectFrame(video: any, model: any) {
        model.detect(video).then((predictions: DetectedObject[]) => {
            renderPredictions(predictions);
            requestAnimationFrame(() => {
                detectFrame(video, model);
            });
        });
    }

    function renderPredictions(predictions: DetectedObject[]) {
        if (canvasRef && canvasRef.current) {
            const ctx = canvasRef.current.getContext("2d");
            if (ctx) {
                ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
                const font = "16px sans-serif";
                ctx.font = font;
                ctx.textBaseline = "top";
                const factor =  0;
                predictions.forEach(prediction => {
                    const x = prediction.bbox[0] + factor;
                    const y = prediction.bbox[1] +factor ;
                    const width = prediction.bbox[2] +factor ;
                    const height = prediction.bbox[3] + factor;
                    ctx.strokeStyle = "#00FF00";
                    ctx.lineWidth = 4;
                    ctx.strokeRect(x, y, width, height);
                    ctx.fillStyle = "#00FF00";
                    const textWidth = ctx.measureText(prediction.class).width;
                    const textHeight = parseInt(font, 10); // base 10
                    ctx.fillRect(x, y, textWidth + 4, textHeight + 4);
                });

                predictions.forEach(prediction => {
                    const x = prediction.bbox[0] + factor ;
                    const y = prediction.bbox[1] + factor;
                    ctx.fillStyle = "#000000";
                    ctx.fillText(prediction.class, x, y);
                });
            }

        }
    }
    return (
        <Fragment>
            <video
                className="video"
                autoPlay
                playsInline
                muted
                ref={videoRef}
                width="600"
                height="500"
            />
            <canvas
                className="canvas"
                ref={canvasRef}
                width="600"
                height="500"
            />
        </Fragment>
    );
}
export default Inference