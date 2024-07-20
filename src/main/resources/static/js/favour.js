
import React, { useState } from 'react@18.2.0';
import { useAnimate } from "framer-motion@10.12.18"
import {createRoot} from 'react-dom@18.2.0/client';

const randomNumberBetween = (min, max) => {
    return Math.floor(Math.random() * (max - min + 1) + min)
}

function LikeButtonAnimate({
                               className,
                               count,
                               name,
                           }) {
    return (
        <>
            <svg viewBox="0 0 24 24" className={className}>
                <defs>
                    <g id={`heart-${name}`}>
                        <path
                            className="fill-inherit"
                            d="M20.884 13.19c-1.351 2.48-4.001 5.12-8.379 7.67l-.503.3-.504-.3c-4.379-2.55-7.029-5.19-8.382-7.67-1.36-2.5-1.41-4.86-.514-6.67.887-1.79 2.647-2.91 4.601-3.01 1.651-.09 3.368.56 4.798 2.01 1.429-1.45 3.146-2.1 4.796-2.01 1.954.1 3.714 1.22 4.601 3.01.896 1.81.846 4.17-.514 6.67z"
                        />
                    </g>
                </defs>
                <mask id={`mask-${name}`} stroke="none">
                    <rect
                        x={0}
                        y={0}
                        height="100%"
                        width="100%"
                        fill="white"
                        className="transition-transform"
                        style={{ transform: `translateY(${((5 - count) / 5) * 100}%)` }}
                    />
                </mask>
                <use
                    x="0"
                    y="0"
                    xlinkHref={`#heart-${name}`}
                    fill="#D1D1D1"
                />

                <use
                    x="0"
                    y="0"
                    fill="#f91880"
                    mask={`url(#mask-${name})`}
                    xlinkHref={`#heart-${name}`}
                />
            </svg>
        </>
    )
}

function LikeButtonDemo(){
    const [count, setCount] = useState(0)
    const [scope ,animate] = useAnimate()

    const handleClick = ()=>{
        const newCount = count === 5 ? 0 : count + 1
        setCount(newCount)

        const sparkles = Array.from({length:20})
        const sparklesAnimation = sparkles.map((_, index) => [
            `.sparkles_${index}`,
            {
                x: randomNumberBetween(-80, 80),
                y: randomNumberBetween(-80, 80),
                scale: randomNumberBetween(1.8, 2.1),
                opacity: 1,
            },
            {
                duration: 0.3,
                at: '<',
            },
        ])

        const sparklesFadeOut = sparkles.map((_, index) => [
            `.sparkles_${index}`,
            {
                opacity: 0,
                scale: 0,
            },
            {
                duration: 0.1,
                at: '<',
            },
        ])

        const sparklesReset = sparkles.map((_, index) => [
            `.sparkles_${index}`,
            {
                x: 0,
                y: 0,
            },
            {
                duration: 0.000001,
            },
        ])

        if (count === 4) {
            animate([
                ...sparklesReset,
                ['button', { scale: 0.9 }, { duration: 0.1 }],
                ...sparklesAnimation,
                ['.counter-one', { y: -12, opacity: 0 }, { duration: 0.2 }],
                ['button', { scale: 1 }, { duration: 0.1, at: '<' }],
                ['.counter-one', { y: 0, opacity: 1 }, { duration: 0.2, at: '<' }],
                ['.counter-one', { y: -12, opacity: 0 }, { duration: 0.6 }],
                ...sparklesFadeOut,
            ])
        } else {
            animate([
                ['button', { scale: 0.9 }, { duration: 0.1 }],
                ['.counter-one', { y: -12, opacity: 0 }, { duration: 0.2 }],
                ['button', { scale: 1 }, { duration: 0.1, at: '<' }],
                ['.counter-one', { y: 0, opacity: 1 }, { duration: 0.2, at: '<' }],
                ['.counter-one', { y: -12, opacity: 0 }, { duration: 0.6 }],
            ])
        }
    }

    return (
        <div ref={scope} className="flex items-center justify-center h-screen w-full">
            <button
                tabIndex={-1}
                className="relative rounded-full"
                onClick={handleClick}
            >
                <LikeButtonAnimate
                    name="main-example"
                    className="h-20 w-20"
                    count={count}
                />
                <span
                    aria-hidden
                    className="pointer-events-none absolute inset-0 -z-10 block"
                >
          {Array.from({ length: 20 }).map((_, index) => (
              <LikeButtonAnimate
                  name={`sparkles-button-example_${index}`}
                  key={index}
                  className={`absolute left-1/2 top-1/2 h-2 w-2 opacity-0 sparkles_${index}`}
                  count={5}
              />
          ))}
        </span>
            </button>

            <div className="min-w-[75px] pl-2 text-2xl font-bold">
        <span
            className={`relative ${count ? 'active' : 'text-zinc-400'}`}
        >
          {count}
            <div className="pointer-events-none absolute -right-6 top-0 ">
            <div className="counter-one text-sm text-zinc-600 opacity-0 dark:text-zinc-100">
              +1
            </div>
          </div>
        </span>
            </div>
        </div>
    )
}
const root = createRoot(document.getElementById('app'));
root.render(<LikeButtonDemo />);
