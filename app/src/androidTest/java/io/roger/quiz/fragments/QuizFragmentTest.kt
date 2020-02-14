package io.roger.quiz.fragments

import io.roger.quiz.data.Person
import io.roger.quiz.data.PersonDatabase
import io.roger.quiz.data.PersonRepository
import kotlinx.coroutines.runBlocking
import org.junit.*

internal class QuizFragmentTest{

    @Test
    fun scoreUpdate_DisplayedInUI() = runBlocking{

        //Given
        val person = Person(name = "daenerys targaryen", photo = "iVBORw0KGgoAAAANSUhEUgAAANwAAAE8CAIAAAAkC0jgAAABEWlDQ1BTa2lhAAAokYWRoU7DUBSG\n" +
                "PwqGhAQEYgJxBQIDAZYQBGqIBlu2ZODarhSStWtuu4wXIBgMBk14CHgFPAEDD4EgaP6uojUb/825\n" +
                "58vJyb33PxecFpKzBklaWM/tmP7ZuaEhP8wzZmsBfj/KHd625/TN0vIgykPlb0VhdbmOHIjX44qv\n" +
                "Sw4qvivZdr1j8aN4K25w0OBJkRXi17I/zGzJX+KjZDgO63ezEqW9U+W+YoN9dhSGLhaflJwLIrFh\n" +
                "whUFl6IcD5eOyFVPwlj1f1TPc6S3Hf7A4n1dCx7g5RZan3Vt8wlWb+D5PfOtPy0tKZx2u5rzvDvq\n" +
                "v5jh1Uy9uoy0YoZyaDiR21DujfzvssfBH0tmRP2ZPyMqAAAAA3NCSVQICAjb4U/gAAAgAElEQVR4\n" +
                "nFS7V7Mk6XEl6OIToTLz3lt1S3UVGqIbIIiGImaGIEFyd43zl9ds3nbW5nGFrdkOlwJAN0R3yasy\n" +
                "M8Sn3H0fCjPk+FNYvH1hJ77jfs5xvHx8qaK5ZgCABn8qBEO7uLh4cv1kGqdXL15x5FprzbXUAgBo\n" +
                "ZKDAzOQJFRUMAcibqiCoCgGDViBq0lC0SiP2UlvTXCuYiaFOsWPnzMxUY/ACYUtbbdl5Z2pgQGS7\n" +
                "3XR9/XSdz4ZIjPd3p1bbNPWPLi/HfhTVrhvDMDjvCRjBqlRjfPni29//4Z9/8vRFPwylFVAoJeWU\n" +
                "b25vf/3Pv/mnf/knU5mm3TT0zrn7u/v7u5t529Rwv5+eP35igFna+eFBwMZ+guCt5FZTyeq9uehK\n" +
                "XggYjLZ1CQTcMQGLtvvTXFvufNi2rVlzziGiiAzd0Fr7+Okch1pqkRpDfP7s+fc//6wP3c3d3dvX\n" +
                "b07Hh6pGjM2a1jaO47A7XBwu5uX47v27Vo1MSq7AXGtS46HrzAwAStpiH1QREU0ByYicdx5QmVkF\n" +
                "YsfsfMm15lREU8reeVVxBOyjC2Hso5VWrXnnchVAJNXLw0W3H01pOhxevXjuUOdlmZe6O0w//Pyz\n" +
                "w8WViL5+9/4f//H/u3n3JpfyMG9d55tq8EGb9EPnvWuiJZe379+XXHNez8u8rfNyWphArObcQCEE\n" +
                "XxRL2hz7GDsHCggIAKb28QEQjIyZCYiRx34spTAwIoqKgmpTAvLBk0ePZOAU1aSaKiKAVCTXWiml\n" +
                "eM/SDAwIEaRmyU0psgGiKmVRL40DETvj2GoiAE9gaoSmIi5M4/5ijP00Hc7b2nIe+rSqNdU1FVaP\n" +
                "HnwIojB249T33kf0SByfPrm+2B/YO2bqKIIhgG7bltacaxYDSeV+eXfvAzunLeeWu368uLxyDKGL\n" +
                "RaRsG5Pvh27c7Wqt1TSn2bCO0wGJQgy6resy57xVJm9Yq5bago+xo3Vb1dSTr6USkyMWFediE8hp\n" +
                "weD6GCAZk+v7kdmF4LsQkIhj3JaziQEgAizL2prc3LwTVQRSKc4553jLGzvvMLRWTJXZsXME5BwB\n" +
                "wjgMoRtVZV232ioBGUApzZnUXGqpLvTRCzMQBUALjkPgNVVQ8QxmWHIGxsAhb4WDe/L0yeHyMqVa\n" +
                "anKEMfp5Pn/55VfT/hiD/+3vvvrD66+tCajE6C3nKg3VmmjXBzNtpZ1Op1pzTsvp4Tgv21o2Kck5\n" +
                "j8iOEbyVWluDEAKq5HR2oCAqpoaKf7omCRw7BGTPMURyJCxDHLa0EVHkrlFlYib2zhsFUPHOtQqi\n" +
                "CIRQxUBFTVVybmamhs5TcM6L884Y3ZYbEXjvHRCylSJZthAIQEIc1ayVzcUw9bHkmqV23nlURe6G\n" +
                "QyoPorbmMsRhir1K6/ppGvcX+30McXe45OAuLi/GcQKC1uTjXbKltG1pXdbbh7ttORNYrlXyKrWG\n" +
                "YRy7WJs5osFxyrmpWpNcW8/+YrfLtaQUzg/HWloq7bAf13WlLqbTUU1A8bQUBlHEVDKjNTU180RE\n" +
                "ro+9d845JyI8DAhWam3amGkY+i72p9Mppw3ZXT06PH16fXN7+3C8r6U1VSQCREByxFIysstNUcWM\n" +
                "mxBjNVPv2SGYY2aSpi4OwzBcXD1GwNPxdHP3dpkXQOcIRJyoGRGTWYwqDQliP6A55/wAbS1KhnlN\n" +
                "IQ4xokfX9wP7OA5T3w9pW0D0eFyYgZ27e7i/ub1PaXv97t1pPfd936MDlWwWvUMmQkzLcpZ6PC/L\n" +
                "vKRUzqfTup2lmdQWvJda2TsibipmYNCkqWNw5BwAqOnHCxIMzAwFnXfIGH00MFEhJEIKLmTIOefI\n" +
                "wRC9d0SeGACxKRAzk2VRcG6KYUtbq83qBkgKiEBAiADR+YJAFXvvyftcsgOPBAhVqzeC2rIhuxAU\n" +
                "QEwi6ZaSAdRaEQAQHKH3kZkF2Tvf9wP1kTxB9BeXVxcXl6Hj2O3ABZW65ZWZa6vbebu9vf/yD1+e\n" +
                "7x5KU6m5tmJNlKDOD1Y7BM55LWsTdKVVEU0l00LP6ck0jc1snMb57f3Nze3p9NAFRsRa2tgNTbRp\n" +
                "TrkSkFpmjn0Xz6eStfZ9B6ZKIKrIaLUiUT8MUvMwTIx8PN403YGo8/7q6tFuGi+vLn//h9dv371W\n" +
                "0T7EXJIJMGMGs6bM2CrGLqrU1lSNvIIfuiYNgF1wImlb6PLQlNkHJEA0UJDSFACRIMZOTIMHcx2z\n" +
                "B21EYGBiVVrG2I+HHUffu9D1E6ER05YLr2tV3XKal3k67GMc5nnZcrr78DYta/SBkbdaTI3RASKq\n" +
                "aWvHZam1Lts6z+fbu4fammklNWgZuHPspQkhIAiDIXLVrBbH6B0wePRiAgIA0Kw58LU1j662SszO\n" +
                "OVYupYhJLRUMmoj3jj0jUc2CKDFGVbeuswgQ45I3A4w+bDUBaAzOeQ8iRGzmtNUQQnBOmV3nU6pj\n" +
                "9CpOtfXe19aIDNHlIkSuHycVXY+nYTrczw9p24YYiX3sw7QblAmZ9+NwcXnVhz50Yz/2zFFqK/lh\n" +
                "2RKgqkjLNW3lw4ebu/uHZdsQZNkWrVXFfN9pK/frFrtRbz4YaCs67XYESEZd1xlRCF3nS25Fmq7l\n" +
                "NE5DDLuybiF4dq7l2sVOmp7npe86AqvVnIu5tZxTDD6vQIDeBzMARXL+4nLyPtw+zLQWH+vD+diF\n" +
                "Dp0LnWulWSu1NgBrtaBarrkZN1GQRtjFyMAozSNIrorEAuajA3PWpFY4WdK3773HLaXT6ayqLsTd\n" +
                "/uL5i+cieblflpQREAhSTR5QWYEQjZwPGFwq6fri4vmzF8G7mtKyzmpNyjbPy9fvbz1BPcrt8chE\n" +
                "ec2tCbMjIueIOaaUAaxJnU9zKi2ndTmd05bO21JzEvaOqKiWWqrI1I1IDICOEaBG7wcXVcz33pmZ\n" +
                "qoICGACAESqbI0NCH52pmho5QocsTEpq6jtPRAiEqIfDJFKzNFDwPqKnsq6igsghONM+1eYwOCAL\n" +
                "FLo+LYsPwXuSZgxEFabYixiYRNcBQuw6FZ1TAsMYhpzNB4YQzlvattVy0dADMXPsu2GYxhcvX10/\n" +
                "eWLOd27k4NbWIgAg3M/r3Yd3y7oAuKnz8+l8f3wANVSzqlIEEH0EZ1oEQOF8Pp+XEzsfu4FrCo52\n" +
                "3dg7JyKl1Pl8Oh1vRcSkSa3LvHpGQNjWYqi15LTlVsqq5jz3XVRTZjJQMe4Dj8PYT0MrZV223OS0\n" +
                "nA67A1PbUsGjObD5fN+2raU8dENpNQRfa1nT5hyiVSvNWlNUJ5VcIHJVigEMg2P2hBjYz2syUWmt\n" +
                "VimlOPaIys4TwGefffc73/n25eFRWpZ/+c2/bDc3jJirlFpc343TpFX3jy7P68nE2JNnil0kQIyh\n" +
                "6Qhq52V7f3uTl1WjX5aVGb3DNUv0vlpTtlzqEEZiXeY5L/O8nLcqy3JsOZ+WrdaMoq1Zs8IO2XsC\n" +
                "XEtx3gUPrSgYIkrkKex6MHUif2oo1RQRERwTCLFDJCUA8MEDAQKKSYVaW6VEMUZz5tmH4FoDVc2m\n" +
                "RGyqtVUkArOaM/jQkfNMAGDICNj1Q\n")


    }

}