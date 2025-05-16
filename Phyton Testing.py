from flask import Flask

app = Flask(__name__)

@app.route('/')
def hello():
    return "Hello from Docker!"

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)


## Phyton Testing

#w = 23
#x = 4 
#y = 10
#z = w * (x + y)
#print (z)

## Phython String

#a1 = "Python"
#a2 = "Amazing"
#a3 = "As Always"
#print (a1,a2,a3)