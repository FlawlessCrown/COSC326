import re
import fileinput

# method to find the index of a substring in a string
def findstring(string, substring, n):
    parts = string.split(substring, n + 1)
    if len(parts) <= n + 1:
        return -1
    return len(string) - len(parts[-1]) - len(substring)


def check_email(x):
    email = x
    # convert string to lowercase.
    email = email.lower()
    email = email.replace("_dot_", ".")
    
    # checks to see if there is a @ in the email address.
    if ('@' in email and email.count('@') == 1):
        check_name(email, x)
    elif (email.count('@') > 1):
        print(x + " <- Invalid mailbox name")
    else:
    # also works if there are multiple "_at_"s.
        if ("_at_" in email and email.count("_at_") == 1):
            email = email.replace("_at_", "@")
            check_name(email, x)
        elif (email.count("_at_") >= 2):
            count = email.count("_at_") - 1
            email = email[:findstring(email, "_at_", count)] + email[findstring(email, "_at_", count):].replace("_at_", "@")
            check_name(email, x)
        else:
            print(x + " <- missing @ symbol")

def check_dash(email):
    mailboxname = email.split('@')[0]
    domainname = email.split('@')[1]
    count = 0
    if (mailboxname[0] == '-' or mailboxname[0] == '_' or mailboxname[0] == '.' or mailboxname[-1] == '-' or mailboxname[-1] == '_' or mailboxname[-1] == '.'):
        return 1
    while (count != len(mailboxname) - 1):
        if ((mailboxname[count] == '-' or mailboxname[count] == '_') and (mailboxname[count+1] == '-' or mailboxname[count+1] == '_')):
            return 1
        elif (mailboxname[count] == '.' and mailboxname[count+1] == '.'):
            return 1
        count = count + 1
    count = 0
    while (count != len(domainname) -1):
        if (domainname[count] == '.' and domainname[count+1] == '.'):
            return 1
        count = count + 1
    return 0

def check_name(email, originalEmail):
    # put possible domain extensions in a list.
    domain = ["co.nz", "com.au", "co.ca", "co.us", "co.uk"]
    mailboxname = email.split('@')[0]
    if (check_dash(email) == 1):
        print(email + " <- Invalid mailbox name")
    elif (re.match(r'^[A-Za-z.0-9_-]+$', mailboxname)):                    
        # checks for valid domain extension
        if (len(email.split('@')[1]) < 5):
            print(originalEmail + " <- Invalid domain name")
        elif (((email.split('@')[1])[0] == '[') and ((email.split('@')[1])[-1] == ']')):
            for a in (((email.split('@')[1])[1:-1]).split('.')):
                if (re.match('^[0-9]*$', a)):
                    int_a = int(a)
                    if (int_a <= 255 and int_a >= 0):
                        pass
                    else:
                        print(originalEmail + " <- Invalid extension")
                        break
                else:
                    print(originalEmail + " <- Invalid extension")
                    break
            else:
                print(email)
        elif (re.match(r'^[a-z.0-9_-]+$', email.split('@')[1])):
            if ((email.split('.')[-1]) == "com"):
                print(email)
            elif ((email.split('.')[-2] + "." + email.split('.')[-1]) in domain):
                print(email)
        else:
            print(originalEmail + " <- Invalid extension")
    else:
        print(originalEmail + " <- Invalid mailbox name")

for email in fileinput.input():
    email = email.rstrip("\n")
    check_email(email)