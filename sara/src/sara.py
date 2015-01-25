from urllib.request import urlopen

def document_it(func):
    def new_function(*args, ** kwargs):
        print('Running function: ', func.__name__)
        print('Positional arguments: ', args)
        print('Keyword arguments: ', kwargs)
        result = func(*args, ** kwargs)
        print('Result: ', result)
        return result
    return new_function

@document_it
def add_ints(a, b):
    return a + b

#print(add_ints(a = 3, b = 5))

#cooler_add_ints = document_it(add_ints)
#print(cooler_add_ints(3, 5))
#    webUrl = urlopen("https://continue.utah.edu")
#    print ("result code " + str(webUrl.getcode()))
#    data = webUrl.read()
#    print (data)

from datetime import datetime

def main(user):
    print("My name is Sara, how can I be of assistance?")
    fileobj = open('run-log.txt', 'a')
    fileobj.write("Run " + str(user) + " @ " + str(datetime.now()) + "\n")
    fileobj.close()
    
    

    
    
if __name__ == "__main__":
    currentUser = "James Elder"
    main(currentUser)   
    
    
