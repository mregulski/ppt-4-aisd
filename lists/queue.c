#include <stdlib.h>
#include <stdio.h>
#include "queue.h"

Queue *q_new()
{
    Queue *q = malloc(sizeof(Queue));
    q->top = NULL;
    q->end = NULL;
    return q;
}

void q_free(Queue *q)
{
    while(!q_isEmpty(q))
    {
        q_pop(q);
    }
}

bool q_isEmpty(Queue *q)
{
    return q->top == NULL && q->end == NULL;
}


void q_push(Queue *q, int x)
{
    Node *e = malloc(sizeof(Node));
    e->value = x;
    e->next = NULL;
    if(q->end != NULL)
    {
        q->end->next = e;
        q->end = e;
    }
    else
    {
        q->top = e;
        q->end = e;
    }
}

int q_pop(Queue *q)
{
    if(q_isEmpty(q))
    {
        fprintf(stderr, "queue is empty\n");
        return -1;
    }
    Node *e = q->top;
    int val = e->value;
    q->top = q->top->next;
    if(q->top == NULL)
        q->end = NULL;
    free(e);
    return val;
}

int q_peek(Queue *q)
{
    return q->top->value;
}

int main(int argc, char **argv)
{
    // TEST 1
    Queue *q1 = q_new();
    q_push(q1,1);
    q_pop(q1);
    printf("empty? %d\n", q_isEmpty(q1));
    free(q1);

    // TEST 2
    Queue *q2 = q_new();
    q_pop(q2);
    q_push(q2,4);
    printf("empty? %d\n", q_isEmpty(q2));
    q_pop(q2);
    free(q2);

    // TEST 3
    Queue *q3 = q_new();
    q_push(q3,1);
    q_push(q3,2);
    q_push(q3,3);
    while(!q_isEmpty(q3))
    {
        printf("pop: %d\n", q_pop(q3));
    }
    printf("empty? %d\n", q_isEmpty(q3));
    free(q3);
    return 0;
}
